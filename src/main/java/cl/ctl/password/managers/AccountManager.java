package cl.ctl.password.managers;

import cl.ctl.password.daos.AccountDAO;
import cl.ctl.password.model.Account;

import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class AccountManager {

    @Inject
    private AccountDAO accountDAO;

    public Account getAccountByClientAndHolding(String client, String holding) throws Exception {
        return accountDAO.getAccountByClientAndHolding(client, holding);
    }

    public List<Account> getAllAccounts() throws Exception {
        return accountDAO.getAllAccounts();
    }

    public Account updateAccount(Account account) throws Exception {
        return accountDAO.updateAccount(account);
    }


}
