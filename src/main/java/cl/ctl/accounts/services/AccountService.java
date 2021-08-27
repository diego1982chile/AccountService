package cl.ctl.accounts.services;

import cl.ctl.accounts.managers.AccountManager;
import cl.ctl.accounts.model.Account;

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
@Path("accounts")
public class AccountService {

    @Inject
    AccountManager accountManager;

    static private final Logger logger = Logger.getLogger(AccountService.class.getName());

    @GET
    public Response getAllAccounts() {
        try {
            List<Account> accounts = accountManager.getAllAccounts();
            return Response.ok(accounts).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @GET
    @Path("{client}/{holding}")
    public Response getAccountByClientAndHolding(@PathParam("client") String client, @PathParam("holding") String holding) {
        try {
            Account account = accountManager.getAccountByClientAndHolding(client, holding);
            return Response.ok(account).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @PUT
    @Path("update")
    public Response updateAccount(Account account) {
        try {
            Account modified = accountManager.updateAccount(account);
            return Response.ok(modified).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @POST
    @Path("new")
    public Response createAccount(Account account) {
        try {
            Account newAccount = accountManager.createAccount(account);
            return Response.ok(newAccount).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path("delete/{id}")
    public Response deleteAccount(@PathParam("id") long id) {
        try {
            long idDeleted = accountManager.deleteAccount(id);
            return Response.ok(idDeleted).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }


}
