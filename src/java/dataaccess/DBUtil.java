package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("CurlingPU");
    
    public static EntityManagerFactory getEmFactory() {
            return EMF;
    }
}