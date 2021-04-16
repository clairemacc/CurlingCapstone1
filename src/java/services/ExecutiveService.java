package services;

import dataaccess.ExecutiveDB;
import java.util.List;
import models.Executive;

/**
 * This class provides functionality pertaining to retrieving executives from the system.
 * @author CurlingCapston
 */
public class ExecutiveService {
    
    /**
     * This method returns a list of all the executives in the system
     * @return list of executives
     */
    public List<Executive> getAll() {
        ExecutiveDB edb = new ExecutiveDB();
        return edb.getAll();
    }
    
    /**
     * This returns a single executive object that corresponds to the userID being searched
     * @param userID - id being searched
     * @return executive object found
     */
    public Executive getByUser(String userID) {
        ExecutiveDB edb = new ExecutiveDB(); 
        return edb.get(userID);
    }
}