package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.ClientDAO;
import cl.ctl.accounts.model.Client;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class ClientManager {

    @Inject
    private ClientDAO clientDAO;


    public List<Client> getAllClients() throws Exception {
        return clientDAO.getAllClients();
    }


}
