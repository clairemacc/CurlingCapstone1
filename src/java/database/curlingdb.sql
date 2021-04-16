DROP SCHEMA IF EXISTS `curlingdb`;
CREATE SCHEMA IF NOT EXISTS `curlingdb` DEFAULT CHARACTER SET latin1;
USE `curlingdb`;

-- -----------------------------------------------------
-- Table `curlingdb`.`league`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`league` (
  `leagueID` VARCHAR(3) NOT NULL,
  `weekday` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`leagueID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`contact` (
  `contactID` VARCHAR(6) NOT NULL, 
  `firstName` VARCHAR(30) NOT NULL, 
  `lastName` VARCHAR(30) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `city` VARCHAR(20) NOT NULL,
  `postal` VARCHAR(20) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`contactID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`role` (
  `roleID` INT(2) NOT NULL,
  `roleName` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`roleID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`user` (
  `userID` VARCHAR(6) NOT NULL,
  `contactID` VARCHAR(6) NOT NULL,
  `role` INT(2) NOT NULL,
  `email` VARCHAR(80) NOT NULL, 
  `password` VARCHAR(20),
  `resetUUID` VARCHAR(80),
  `isActive` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`userID`),
  CONSTRAINT `fk_userToContact` 
    FOREIGN KEY (`contactID`)
    REFERENCES `curlingdb`.`contact`(`contactID`),
  CONSTRAINT `fk_userToRole` 
    FOREIGN KEY (`role`)
    REFERENCES `curlingdb`.`role` (`roleID`));


-- -----------------------------------------------------
-- Table `curlingdb`.`position`
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`position` (
  `positionID` INT(1) NOT NULL,
  `positionName` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`positionID`));


-- -----------------------------------------------------
-- Table `curlingdb`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`team` (
  `teamID` VARCHAR(6) NOT NULL,
  `teamName` VARCHAR(80) NOT NULL,
  `leagueID` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`teamID`),
  CONSTRAINT `fk_teamToLeague`
    FOREIGN KEY (`leagueID`)
    REFERENCES `curlingdb`.`league` (`leagueID`)
    ON DELETE NO ACTION);

-- -----------------------------------------------------
-- Table `curlingdb`.`executive`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`executive` (
  `userID` VARCHAR(6) NOT NULL,
  `leagueID` VARCHAR(3) NOT NULL,
  `execTitle` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `fk_executiveToLeague` 
    FOREIGN KEY (`leagueID`)
    REFERENCES `curlingdb`.`league` (`leagueID`),
  CONSTRAINT `fk_executiveToUser`
    FOREIGN KEY (`userID`)
    REFERENCES `curlingdb`.`user` (`userID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`player` (
  `playerID` VARCHAR(6) NOT NULL,
  `userID` VARCHAR(6) NOT NULL,
  `teamID` VARCHAR(6),
  `position` INT(1) NOT NULL,
  PRIMARY KEY (`playerID`),
  CONSTRAINT `fk_playerToUser`
    FOREIGN KEY (`userID`)
    REFERENCES `curlingdb`.`user` (`userID`)
    ON DELETE NO ACTION,
  CONSTRAINT `fk_playerToTeam`
    FOREIGN KEY (`teamID`)
    REFERENCES `curlingdb`.`team` (`teamID`)
    ON DELETE NO ACTION,
  CONSTRAINT `fk_playerToPosition`
    FOREIGN KEY (`position`) 
    REFERENCES `curlingdb`.`position` (`positionID`));


-- -----------------------------------------------------
-- Table `curlingdb`.`spare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`spare` (
  `spareID` VARCHAR(6) NOT NULL,
  `contactID` VARCHAR(6) NOT NULL,
  `leagueID` VARCHAR(3) NOT NULL, 
  `position` INT(1) NOT NULL,  
  `flexibleP` BOOLEAN DEFAULT FALSE, 
  PRIMARY KEY (`spareID`),
  CONSTRAINT `fk_spareToContact`
    FOREIGN KEY (`contactID`)
    REFERENCES `curlingdb`.`contact` (`contactID`),
  CONSTRAINT `fk_spareToLeague`
    FOREIGN KEY (`leagueID`) 
    REFERENCES `curlingdb`.`league` (`leagueID`),
  CONSTRAINT `fk_spareToPosition`
    FOREIGN KEY (`position`)
    REFERENCES `curlingdb`.`position` (`positionID`));


-- -----------------------------------------------------
-- Table `curlingdb`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`game` (
  `gameID` VARCHAR(6) NOT NULL,
  `homeTeam` VARCHAR(6) NOT NULL,
  `awayTeam` VARCHAR(6) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`gameID`),
CONSTRAINT `fk_homeToTeam`
    FOREIGN KEY (`homeTeam`)
    REFERENCES `curlingdb`.`team` (`teamID`)
    ON DELETE NO ACTION, 
  CONSTRAINT `fk_awayToTeam`
    FOREIGN KEY (`awayTeam`)
    REFERENCES `curlingdb`.`team` (`teamID`)
    ON DELETE NO ACTION);


-- -----------------------------------------------------
-- Table `curlingdb`.`score`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`score` (
  `gameID` VARCHAR(6) NOT NULL,
  `submitter` VARCHAR(6) NOT NULL,
  `homeScore` INT(2) NOT NULL,
  `awayScore` INT(2) NOT NULL,
  `winner` VARCHAR(6),
  `submitDate` DATE NOT NULL,
  PRIMARY KEY (`gameID`),
  CONSTRAINT `fk_scoreToGame` 
    FOREIGN KEY (`gameID`)
    REFERENCES `curlingdb`.`game` (`gameID`)
    ON DELETE NO ACTION,
  CONSTRAINT `fk_scoreToSubmitter`
    FOREIGN KEY (`submitter`)
    REFERENCES `curlingdb`.`user` (`userID`),
  CONSTRAINT `fk_scoreToTeam`
    FOREIGN KEY (`winner`)
    REFERENCES `curlingdb`.`team` (`teamID`));


-- -----------------------------------------------------
-- Table `curlingdb`.`standing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`standing` (
  `teamID` VARCHAR(6) NOT NULL,
  `gamesPlayed` INT(3) NOT NULL DEFAULT 0,
  `gamesWon` INT(3) NOT NULL DEFAULT 0,
  `gamesLost` INT(3) NOT NULL DEFAULT 0,
  PRIMARY KEY (`teamID`),
  CONSTRAINT `fk_standingToTeam`
    FOREIGN KEY (`teamID`)
    REFERENCES `curlingdb`.`team` (`teamID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`registration`
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`registration` (
  `contactID` VARCHAR(6) NOT NULL,
  `position` INT(1) NOT NULL,
  `flexibleP` BOOLEAN DEFAULT FALSE,
  `leagues` VARCHAR(40) NOT NULL,
  `signupAll` BOOLEAN DEFAULT FALSE, 
  `regType` VARCHAR(10) NOT NULL,
  `groupID` VARCHAR(3),
  `teamName` VARCHAR(80),
  `regDate` DATE NOT NULL,
  `isActive` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`contactID`),
  CONSTRAINT `fk_playerRegToContact`
    FOREIGN KEY (`contactID`)
    REFERENCES `curlingdb`.`contact` (`contactID`),
  CONSTRAINT `fk_playerRegToPosition`
    FOREIGN KEY (`position`)
    REFERENCES `curlingdb`.`position` (`positionID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`spareRequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`spareRequest` (
  `requestID` VARCHAR(6) NOT NULL, 
  `submitter` VARCHAR(6) NOT NULL, 
  `position` INT(1) NOT NULL, 
  `teamID` VARCHAR(6) NOT NULL,
  `gameID` VARCHAR(6) NOT NULL,
  `requestDate` DATE NOT NULL,
  `isActive` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`requestID`),
  CONSTRAINT `fk_spareReqToUser`
    FOREIGN KEY (`submitter`)
    REFERENCES `curlingdb`.`user` (`userID`),
  CONSTRAINT `fk_spareReqToPosition`
    FOREIGN KEY (`position`)
    REFERENCES `curlingdb`.`position` (`positionID`),
  CONSTRAINT `fk_spareReqToTeam`
    FOREIGN KEY (`teamID`)
    REFERENCES `curlingdb`.`team` (`teamID`),
  CONSTRAINT `fk_spareReqToGame`
    FOREIGN KEY (`gameID`)
    REFERENCES `curlingdb`.`game` (`gameID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`spareAssigned`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`spareAssigned` (
  `requestID` VARCHAR(6) NOT NULL,
  `spareID` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`requestID`),
  CONSTRAINT `fk_spareAssignedToSpareRequest` 
    FOREIGN KEY (`requestID`)
    REFERENCES `curlingdb`.`spareRequest` (`requestID`),
  CONSTRAINT `fk_spareAssignedToSpare`
    FOREIGN KEY (`spareID`)
    REFERENCES `curlingdb`.`spare` (`spareID`));

-- -----------------------------------------------------
-- Table `curlingdb`.`newsPost` 
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `curlingdb`.`newsPost` (
  `postID` VARCHAR(6) NOT NULL,
  `userID` VARCHAR(6) NOT NULL,
  `title` VARCHAR(30) NOT NULL, 
  `body` TEXT NOT NULL,
  `postDate` DATE NOT NULL,
  PRIMARY KEY (`postID`),
  CONSTRAINT `fk_newsPostToUser` 
    FOREIGN KEY (`userID`)
    REFERENCES `curlingdb`.`user` (`userID`));


-- spare registration = position choice, flexible boolean 

-- modify here!


INSERT INTO `league` VALUES ('L01', 'Tuesday');
INSERT INTO `league` VALUES ('L02', 'Thursday');

INSERT INTO `contact` VALUES ('C_0001', 'Claire', 'MacConnell', '100 1st Street', 'Calgary', 'A1A1A1', '1clairemacconnell@gmail.com', '4031111111');
INSERT INTO `contact` VALUES ('C_0002', 'Madeleine', 'MacConnell', '200 2nd Street', 'Calgary', 'A2A2A2', '1claire_macconnell@hotmail.com', '4032222222');
INSERT INTO `contact` VALUES ('C_0003', 'Brian', 'MacConnell', '300 3rd Street', 'Calgary', 'A3A3A3', 'test3@email.com', '4033333333');
INSERT INTO `contact` VALUES ('C_0004', 'Matthew', 'Dunne', '400 4th Street', 'Calgary', 'A4A4A4', 'test4@email.com', '4034444444');
INSERT INTO `contact` VALUES ('C_0005', 'Alexandra', 'Coombs', '500 5th Street SW', 'Calgary', 'A5A5A5', 'test5@email.com', '4035555555');
INSERT INTO `contact` VALUES ('C_0006', 'Dylan', 'Callaghan', '600 6th Street', 'Calgary', 'A6A6A6', 'test6@email.com', '4036666666');
INSERT INTO `contact` VALUES ('C_0007', 'Chihiro', 'MacConnell', '700 7th Street', 'Calgary', 'A7A7A7', 'test7@email.com', '4037777777');
INSERT INTO `contact` VALUES ('C_0008', 'Clooney', 'MacConnell', '800 8th Street', 'Calgary', 'A8A8A8', 'test8@email.com', '4038888888');
--
INSERT INTO `contact` VALUES ('C_0009', 'Harlon', 'Broom', '900 9th Street', 'Calgary', 'A9A9A9', 'test9@email.com', '4031212121');
INSERT INTO `contact` VALUES ('C_0010', 'Carol', 'Dupont', '1000 10th Street','Calgary',  'B0B0B0', 'test10@email.com', '4032323232');
INSERT INTO `contact` VALUES ('C_0011', 'San', 'Arisa', '1100 11th Street', 'Calgary', 'B1B1B1', 'test11@email.com', '4033434343');
INSERT INTO `contact` VALUES ('C_0012', 'Bill', 'Drummer', '1200 12th Street', 'Calgary', 'B2B2B2', 'test12@email.com', '4034545454');
INSERT INTO `contact` VALUES ('C_0013', 'Kim', 'Kardashian', '1300 13th Street', 'Calgary', 'B3B3B3', 'test13@email.com', '4035656565');
INSERT INTO `contact` VALUES ('C_0014', 'Kourtney', 'Kardashian', '1400 14th Street', 'Calgary', 'B4B4B4', 'test14@email.com', '4036767676');
INSERT INTO `contact` VALUES ('C_0015', 'Khloe', 'Kardashian', '1500 15th Street', 'Calgary', 'B5B5B5', 'test15@email.com', '4037878787');
INSERT INTO `contact` VALUES ('C_0016', 'Rob', 'Kardashian', '1600 16th Street', 'Calgary', 'B6B6B6', 'test16@email.com', '4038989898');
--
INSERT INTO `contact` VALUES ('C_0017', 'Abigail', 'James', '1700 17th Street', 'Calgary', 'B7B7B7', 'test17@email.com', '4033131313');
INSERT INTO `contact` VALUES ('C_0018', 'Feng', 'Bai', '1800 18th Street', 'Calgary', 'B8B8B8', 'test18@email.com', '4033232323');
INSERT INTO `contact` VALUES ('C_0019', 'Aidan', 'Fleming', '1900 19th Street', 'Calgary', 'B9B9B9', 'test19@email.com', '40333434343');
INSERT INTO `contact` VALUES ('C_0020', 'Logan', 'Duong', '2000 20th Street', 'Calgary', 'C0C0C0', 'test20@email.com', '4033535353');
INSERT INTO `contact` VALUES ('C_0021', 'Thomas', 'Jefferson', '2100 21st Street', 'Calgary', 'C1C1C1', 'test21@email.com', '4033636363');
INSERT INTO `contact` VALUES ('C_0022', 'George', 'Washington', '2200 22nd Street', 'Calgary', 'C2C2C2', 'test22@email.com', '4033737373');
INSERT INTO `contact` VALUES ('C_0023', 'Alexander', 'Hamilton', '12300 23rd Street', 'Calgary', 'C3C3C3', 'test23@email.com', '4033838383');
INSERT INTO `contact` VALUES ('C_0024', 'Benjamin', 'Franklin', '2400 24th Street', 'Calgary', 'C4C4C4', 'test24@email.com', '4033939393');

INSERT INTO `contact` VALUES ('C_0025', 'Kayla', 'Kamiko', '2575 25th Street', 'Calgary', 'D1D1D1', 'spare01@email.com', '4035728392');
INSERT INTO `contact` VALUES ('C_0026', 'Justin', 'Bandero','2676 26th Street', 'Calgary', 'D2D2D2', 'spare02email.com', '4031437998');
INSERT INTO `contact` VALUES ('C_0027', 'Tobey', 'Maguire','2771 27th Street', 'Calgary', 'D3D3D3', 'spare03@email.com', '4032435675');
INSERT INTO `contact` VALUES ('C_0028', 'Nathan', 'Long','2878 28th Street', 'Calgary', 'D4D4D4', 'spare04@email.com', '4037689352');

INSERT INTO `contact` VALUES ('C_0029', 'Marten', 'Fhedrik', '2999 29th Street', 'Calgary', 'D5D5D5', 'spare05@email.com', '4038865956');
INSERT INTO `contact` VALUES ('C_0030', 'Ahmed', 'Muhtmali','3000 30th Street', 'Calgary', 'D6D6D6', 'spare06email.com', '4034565528');
INSERT INTO `contact` VALUES ('C_0031', 'Boris', 'Ellington','3101 31st Street', 'Calgary', 'D7D7D7', 'claire_macconnell@hotmail.com', '4038896656');
INSERT INTO `contact` VALUES ('C_0032', 'Peony', 'Flora','3232 32nd Street', 'Calgary', 'D8D8D8', 'clairemacconnell@gmail.com', '4035010023');

INSERT INTO `contact` VALUES ('C_0033', 'Angela', 'Administrator', '123 123 Street', 'Calgary', 'H0H0H0', 'oclairemacconnell@gmail.com', '4038133926');
INSERT INTO `contact` VALUES ('C_0034', 'Admin', 'User', '000 Zero Street', 'Calgary', 'A0A0A0', 'admin@email.com', '4030000000');

INSERT INTO `role` VALUES (1, 'League Executive');
INSERT INTO `role` VALUES (2, 'Player');

INSERT INTO `position` VALUES (1, 'Lead');
INSERT INTO `position` VALUES (2, 'Second');
INSERT INTO `position` VALUES (3, 'Third');
INSERT INTO `position` VALUES (4, 'Skip');


-- add an admin user here

INSERT INTO `user` VALUES ('U_0001', 'C_0001', 2, 'test1@email.com', 'password1', null, true);
INSERT INTO `user` VALUES ('U_0002', 'C_0002', 2, 'test2@email.com', 'password2', null, true);
INSERT INTO `user` VALUES ('U_0003', 'C_0003', 2, 'test3@email.com', 'password3', null, true);
INSERT INTO `user` VALUES ('U_0004', 'C_0004', 2, 'test4@email.com', 'password4', null, true);
INSERT INTO `user` VALUES ('U_0005', 'C_0005', 2, 'test5@email.com', 'password5', null, true);
INSERT INTO `user` VALUES ('U_0006', 'C_0006', 2, 'test6@email.com', 'password6', null, true);
INSERT INTO `user` VALUES ('U_0007', 'C_0007', 2, 'test7@email.com', 'password7', null, true);
INSERT INTO `user` VALUES ('U_0008', 'C_0008', 2, 'test8@email.com', 'password8', null, true);
--
INSERT INTO `user` VALUES ('U_0009', 'C_0009', 2, 'test9@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0010', 'C_0010', 2, 'test10@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0011', 'C_0011', 2, 'test11@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0012', 'C_0012', 2, 'test12@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0013', 'C_0013', 2, 'test13@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0014', 'C_0014', 2, 'test14@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0015', 'C_0015', 2, 'test15@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0016', 'C_0016', 2, 'test16@email.com', 'password', null, true);
--
INSERT INTO `user` VALUES ('U_0017', 'C_0017', 2, 'test17@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0018', 'C_0018', 2, 'test18@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0019', 'C_0019', 2, 'test19@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0020', 'C_0020', 2, 'test20@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0021', 'C_0021', 2, 'test21@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0022', 'C_0022', 2, 'test22@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0023', 'C_0023', 2, 'test23@email.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0024', 'C_0024', 2, 'test24@email.com', 'password', null, true);

INSERT INTO `user` VALUES ('U_0025', 'C_0033', 1, 'oclairemacconnell@gmail.com', 'password', null, true);
INSERT INTO `user` VALUES ('U_0026', 'C_0034', 1, 'admin@email.com', 'password', null, true);


INSERT INTO `executive` VALUES ('U_0025', 'L01', 'Volunteer Coordinator');
INSERT INTO `executive` VALUES ('U_0026', 'L02', 'President');
--

INSERT INTO `team` VALUES ('T_0001', 'The Chimney Sweepers', 'L01');
INSERT INTO `team` VALUES ('T_0002', 'We Throw Rocks', 'L01');
INSERT INTO `team` VALUES ('T_0003', 'Army of Ice', 'L01');
INSERT INTO `team` VALUES ('T_0004', 'The Kardashian Kurlers', 'L02');
INSERT INTO `team` VALUES ('T_0005', 'Cap-Stones', 'L02');
INSERT INTO `team` VALUES ('T_0006', 'The Founding Fathers', 'L02');
INSERT INTO `team` VALUES ('T_0007', 'Gilmore Girls', 'L01');
INSERT INTO `team` VALUES ('T_0008', 'Team Canada', 'L02');

INSERT INTO `player` VALUES ('P_0001', 'U_0001', 'T_0001', 1); --
INSERT INTO `player` VALUES ('P_0002', 'U_0001', 'T_0008', 1); --
--
INSERT INTO `player` VALUES ('P_0003', 'U_0002', 'T_0001', 1);
INSERT INTO `player` VALUES ('P_0004', 'U_0003', 'T_0001', 3);
INSERT INTO `player` VALUES ('P_0005', 'U_0004', 'T_0001', 4);
INSERT INTO `player` VALUES ('P_0006', 'U_0005', 'T_0002', 1);
INSERT INTO `player` VALUES ('P_0007', 'U_0006', 'T_0002', 2);
INSERT INTO `player` VALUES ('P_0008', 'U_0007', 'T_0002', 3); --
INSERT INTO `player` VALUES ('P_0009', 'U_0007', 'T_0008', 3); --
--
INSERT INTO `player` VALUES ('P_0010', 'U_0008', 'T_0002', 4);
INSERT INTO `player` VALUES ('P_0011', 'U_0009', 'T_0003', 1);
INSERT INTO `player` VALUES ('P_0012', 'U_0010', 'T_0003', 2); --
INSERT INTO `player` VALUES ('P_0013', 'U_0010', 'T_0008', 2); --
--
INSERT INTO `player` VALUES ('P_0014', 'U_0011', 'T_0003', 3);
INSERT INTO `player` VALUES ('P_0015', 'U_0012', 'T_0003', 4);
INSERT INTO `player` VALUES ('P_0016', 'U_0013', 'T_0004', 1);
INSERT INTO `player` VALUES ('P_0017', 'U_0014', 'T_0004', 2);
INSERT INTO `player` VALUES ('P_0018', 'U_0015', 'T_0004', 3);
INSERT INTO `player` VALUES ('P_0019', 'U_0016', 'T_0004', 4); --
INSERT INTO `player` VALUES ('P_0020', 'U_0016', 'T_0007', 4); --
--
INSERT INTO `player` VALUES ('P_0021', 'U_0017', 'T_0005', 1);
INSERT INTO `player` VALUES ('P_0022', 'U_0018', 'T_0005', 2);
INSERT INTO `player` VALUES ('P_0023', 'U_0019', 'T_0005', 3); --
INSERT INTO `player` VALUES ('P_0024', 'U_0019', 'T_0007', 3); --
--
INSERT INTO `player` VALUES ('P_0025', 'U_0020', 'T_0005', 4);
INSERT INTO `player` VALUES ('P_0026', 'U_0021', 'T_0006', 1); --
INSERT INTO `player` VALUES ('P_0027', 'U_0021', 'T_0007', 1); --
--
INSERT INTO `player` VALUES ('P_0028', 'U_0022', 'T_0006', 2);
INSERT INTO `player` VALUES ('P_0029', 'U_0023', 'T_0006', 3);
INSERT INTO `player` VALUES ('P_0030', 'U_0024', 'T_0006', 4);
INSERT INTO `player` VALUES ('P_0031', 'U_0020', 'T_0004', 1);
INSERT INTO `player` VALUES ('P_0032', 'U_0018', 'T_0004', 2);


INSERT INTO `game` VALUES ('G_0001', 'T_0001', 'T_0002', '2021-01-5');
INSERT INTO `game` VALUES ('G_0004', 'T_0004', 'T_0005', '2021-01-14');
INSERT INTO `game` VALUES ('G_0002', 'T_0002', 'T_0003', '2021-01-19');
INSERT INTO `game` VALUES ('G_0005', 'T_0005', 'T_0006', '2020-05-28');
INSERT INTO `game` VALUES ('G_0003', 'T_0003', 'T_0001', '2021-05-02');
INSERT INTO `game` VALUES ('G_0006', 'T_0006', 'T_0004', '2021-05-11');
INSERT INTO `game` VALUES ('G_0007', 'T_0007', 'T_0008', '2021-05-24');


INSERT INTO `standing`(`teamID`) VALUES('T_0001');
INSERT INTO `standing`(`teamID`) VALUES('T_0002');
INSERT INTO `standing`(`teamID`) VALUES('T_0003');
INSERT INTO `standing`(`teamID`) VALUES('T_0004');
INSERT INTO `standing`(`teamID`) VALUES('T_0005');
INSERT INTO `standing`(`teamID`) VALUES('T_0006');
INSERT INTO `standing`(`teamID`) VALUES('T_0007');
INSERT INTO `standing`(`teamID`) VALUES('T_0008');

INSERT INTO `spare` VALUES ('S_0001', 'C_0025', 'L01', 1, false);
INSERT INTO `spare` VALUES ('S_0002', 'C_0026', 'L01', 2, true);
INSERT INTO `spare` VALUES ('S_0003', 'C_0027', 'L01', 3, false);
INSERT INTO `spare` VALUES ('S_0004', 'C_0028', 'L01', 4, true);
INSERT INTO `spare` VALUES ('S_0005', 'C_0029', 'L02', 1, true);
INSERT INTO `spare` VALUES ('S_0006', 'C_0030', 'L02', 2, false);
INSERT INTO `spare` VALUES ('S_0007', 'C_0031', 'L02', 3, true);
INSERT INTO `spare` VALUES ('S_0008', 'C_0032', 'L02', 4, true);

INSERT INTO `newsPost` VALUES('N_0001', 'U_0026', 'Lorem Ipsum',
 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
'2021-04-15');