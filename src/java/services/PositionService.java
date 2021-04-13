package services;

import dataaccess.PositionDB;
import java.util.List;
import models.Position;

public class PositionService {
    public List<Position> getAll() {
        PositionDB pdb = new PositionDB();
        return pdb.getAll();
    }
    
    public Position getByPosID(int positionID) {
        PositionDB pdb = new PositionDB();
        return pdb.getByPosID(positionID);
    }
    
    public Position getByPosName(String positionName) {
        PositionDB pdb = new PositionDB();
        return pdb.getByPosName(positionName);
    }
    
    
}
