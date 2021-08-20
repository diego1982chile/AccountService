package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.ClientDAO;
import cl.ctl.accounts.daos.HoldingDAO;
import cl.ctl.accounts.model.Client;
import cl.ctl.accounts.model.Holding;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class HoldingManager {

    @Inject
    private HoldingDAO holdingDAO;


    public List<Holding> getAllHoldings() throws Exception {
        return holdingDAO.getAllHoldings();
    }


}
