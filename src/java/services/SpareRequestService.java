package services;

import dataaccess.SpareRequestDB;
import java.util.Date;
import java.util.List;
import models.Game;
import models.Position;
import models.SpareRequest;
import models.Team;
import models.User;

public class SpareRequestService {
    
    public SpareRequest get(String requestID) {
        SpareRequestDB srdb = new SpareRequestDB();
        return srdb.get(requestID);
    }
    
    public List<SpareRequest> getAll() {
        SpareRequestDB srdb = new SpareRequestDB();
        return srdb.getAll();
    } 
    
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
    
    public void update(String requestID) {
        SpareRequestDB srdb = new SpareRequestDB();
        SpareRequest spRequest = get(requestID);
        
        spRequest.setIsActive(false);
        srdb.update(spRequest);
    }
    
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