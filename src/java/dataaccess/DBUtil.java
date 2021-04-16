package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class is used to create the entity manager factory for use in accessing
 * the database using java persistence api.
 * @author CurlingCapstone
 */
public class DBUtil {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("CurlingPU");
    
    public static EntityManagerFactory getEmFactory() {
            return EMF;
    }
}