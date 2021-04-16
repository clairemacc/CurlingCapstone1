package services;

import dataaccess.PositionDB;
import java.util.List;
import models.Position;

/**
 * This provides functionality pertaining to the position objects.
 * 
 */
public class PositionService {
    /**
     * This method returns a list of all the positions in the database
     * @return list of all positions
     */
    public List<Position> getAll() {
        PositionDB pdb = new PositionDB();
        return pdb.getAll();
    }
    
    /**
     * This returns the position object that corresponds with the positionID being searched
     * @param positionID - unique identifier for each position object
     * @return position object found
     */
    public Position getByPosID(int positionID) {
        PositionDB pdb = new PositionDB();
        return pdb.getByPosID(positionID);
    }
    
    /**
     * This returns the position object that corresponds with the positionName being searched
     * @param positionName - name of the position being searched
     * @return position object found
     */
    public Position getByPosName(String positionName) {
        PositionDB pdb = new PositionDB();
        return pdb.getByPosName(positionName);
    }
    
    
}
