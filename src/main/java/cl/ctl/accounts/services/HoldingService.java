package cl.ctl.accounts.services;

import cl.ctl.accounts.managers.ClientManager;
import cl.ctl.accounts.managers.HoldingManager;
import cl.ctl.accounts.model.Client;
import cl.ctl.accounts.model.Holding;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
@Path("holdings")
public class HoldingService {

    @Inject
    HoldingManager holdingManager;

    static private final Logger logger = Logger.getLogger(HoldingService.class.getName());

    @GET
    public Response getAllHoldings() {
        try {
            List<Holding> holdings = holdingManager.getAllHoldings();
            return Response.ok(holdings).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }

    @POST
    @Path("new")
    public Response createClient(Holding holding) {
        try {
            Holding newHolding = holdingManager.createHolding(holding);
            return Response.ok(newHolding).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }


}
