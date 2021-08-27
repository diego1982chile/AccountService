package cl.ctl.accounts.services;

import cl.ctl.accounts.managers.AccountManager;
import cl.ctl.accounts.managers.UserManager;
import cl.ctl.accounts.model.Account;
import cl.ctl.accounts.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Path("users")
public class UserService {

    @Inject
    UserManager userManager;

    static private final Logger logger = Logger.getLogger(UserService.class.getName());

    @POST
    @Path("authenticate")
    public Response authenticate(User user) {
        try {
            User authenticated = userManager.authenticateUser(user);
            return Response.ok(authenticated).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @POST
    @Path("new")
    public Response getAccountByClientAndHolding(User user) {
        try {
            User persisted = userManager.createUser(user);
            return Response.ok(persisted).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }


}
