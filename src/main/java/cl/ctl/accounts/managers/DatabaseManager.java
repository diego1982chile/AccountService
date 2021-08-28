package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.AccountDAO;
import cl.ctl.accounts.daos.DatabaseDAO;
import cl.ctl.accounts.model.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class DatabaseManager {

    @Inject
    private DatabaseDAO databaseDAO;

    public void createDatabaseObjects() throws Exception {

        databaseDAO.executeScript("client.sql");
        databaseDAO.executeScript("holding.sql");
        databaseDAO.executeScript("account.sql");
        databaseDAO.executeScript("user.sql");

        databaseDAO.populateTableClient();
        databaseDAO.populateTableHolding();

    }



}
