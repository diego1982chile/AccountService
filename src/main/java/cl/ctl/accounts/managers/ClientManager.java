package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.ClientDAO;
import cl.ctl.accounts.model.Account;
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

    public Client createClient(Client client) throws Exception {
        return clientDAO.createClient(client);
    }

    public Client updateClient(Client client) throws Exception {
        return clientDAO.updateClient(client);
    }

    public long deleteClient(long id) throws Exception {
        return clientDAO.deleteClient(id);
    }

}
