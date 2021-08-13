package cl.ctl.password.model;

import static cl.ctl.password.model.DAO.NON_PERSISTED_ID;

/**
 * Created by root on 09-08-21.
 */
public class Holding {

    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    private String name;

    public Holding() {
    }

    public Holding(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
