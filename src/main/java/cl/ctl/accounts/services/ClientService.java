package cl.ctl.accounts.services;

import cl.ctl.accounts.managers.AccountManager;
import cl.ctl.accounts.managers.ClientManager;
import cl.ctl.accounts.model.Account;
import cl.ctl.accounts.model.Client;

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
@Path("clients")
public class ClientService {

    @Inject
    ClientManager clientManager;

    static private final Logger logger = Logger.getLogger(ClientService.class.getName());

    @GET
    public Response getAllClients() {
        try {
            List<Client> clients = clientManager.getAllClients();
            return Response.ok(clients).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }


}
