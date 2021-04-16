package services;

import dataaccess.SpareDB;
import dataaccess.SpareRequestDB;
import java.util.Date;
import java.util.List;
import models.Game;
import models.Position;
import models.Spare;
import models.SpareAssigned;
import models.SpareRequest;
import models.Team;
import models.User;

/**
 * This class provides functionality such as retrieve, insert, update and delete for the spare request object
 * 
 */
public class SpareRequestService {
    
    /**
     * This returns a spare request with the ID being searched
     * @param requestID - unique ID of a spare request
     * @return spare request object found
     */
    public SpareRequest get(String requestID) {
        SpareRequestDB srdb = new SpareRequestDB();
        return srdb.get(requestID);
    }
    
    /**
     * This returns a list of all spare requests in the system
     * @return list of spare request objects found
     */
    public List<SpareRequest> getAll() {
        SpareRequestDB srdb = new SpareRequestDB();
        return srdb.getAll();
    } 
    
    /**
     * This returns a list of all active spare requests in the system
     * @return list of active spare request objects found
     */
    public List<SpareRequest> getAllActive() {
        SpareRequestDB srdb = new SpareRequestDB();
        return srdb.getAllActive();
    }
    
    /**
     * This method inserts a new spare request into the system
     * @param submitter - user object to represent the user that submitted the spare request
     * @param position - position in need of spare
     * @param team - team in need of spare
     * @param game - game in need of spare
     * @param date - date for spare request
     * @return spareRequest - newly created spare request
     */
    public SpareRequest insert(User submitter, Position position, Team team, Game game, Date date) {
        SpareRequestDB srdb = new SpareRequestDB();
        
        String requestID = generateRequestID();
        SpareRequest spRequest = new SpareRequest(requestID, date);
        
        spRequest.setSubmitter(submitter);
        spRequest.setPosition(position);
        spRequest.setTeamID(team);
        spRequest.setGameID(game);
        spRequest.setIsActive(true);
        
        srdb.insert(spRequest);
        return spRequest;
    }
    
    /**
     * This method updates a spare request with the selected spare
     * @param requestID - unique ID of a spare request
     * @param spareID - unique ID of a spare 
     * @return 
     */
    public boolean insertSpareAssigned(String requestID, String spareID) {
        SpareRequestDB srdb = new SpareRequestDB();
        SpareRequest spRequest = get(requestID);
        Spare spare = new SpareDB().getBySpareID(spareID);
        
        SpareAssigned spAssigned = new SpareAssigned(requestID);
        spAssigned.setSpareRequest(spRequest);
        spAssigned.setSpareID(spare);
        
        return srdb.insertSpareAssigned(spAssigned);
    }
    
    /**
     * This method deactivates a spare request
     * @param spRequest - spare request object to be deactivated
     */
    public void deactivate(SpareRequest spRequest) {
        SpareRequestDB srdb = new SpareRequestDB();
        
        spRequest.setIsActive(false);
        srdb.update(spRequest);
    }
    
    /**
     * This generates a requestID for a new sapreRequest object
     * @return requestID - the newly generated ID
     */
    public String generateRequestID() {
        List<SpareRequest> spRequests = getAll();
        int idNum; 
        
        if (spRequests == null || spRequests.isEmpty()) 
            idNum = 0;
        else {
            String idStr = spRequests.get(spRequests.size() - 1).getRequestID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }

        String requestID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            requestID = "R_000" + newIdNum;
        else if (newIdNum <= 99) 
            requestID = "R_00" + newIdNum;
        else if (newIdNum <= 999)
            requestID = "R_0" + newIdNum;
        else 
            requestID = "R_" + newIdNum;
        
        return requestID;        
    }
}
