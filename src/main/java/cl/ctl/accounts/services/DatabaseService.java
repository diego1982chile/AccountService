package cl.ctl.accounts.services;

import cl.ctl.accounts.managers.AccountManager;
import cl.ctl.accounts.managers.DatabaseManager;
import cl.ctl.accounts.model.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by des01c7 on 12-12-19.
 */
@RequestScoped
@Produces(APPLICATION_JSON)
@Path("database")
public class DatabaseService {

    @Inject
    DatabaseManager databaseManager;

    static private final Logger logger = Logger.getLogger(DatabaseService.class.getName());

    @GET
    @Path("init")
    public Response init() {
        try {
            databaseManager.createDatabaseObjects();

            JsonObject json = Json.createObjectBuilder()
                            .add("message", "Objetos de BD creados exitosamente").build();

            return Response.ok(json).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

}
