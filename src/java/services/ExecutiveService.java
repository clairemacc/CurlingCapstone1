package services;

import dataaccess.ExecutiveDB;
import java.util.List;
import models.Executive;

public class ExecutiveService {
    
    public List<Executive> getAll() {
        ExecutiveDB edb = new ExecutiveDB();
        return edb.getAll();
    }
    
    public Executive getByUser(String userID) {
        ExecutiveDB edb = new ExecutiveDB(); 
        return edb.get(userID);
    }
}