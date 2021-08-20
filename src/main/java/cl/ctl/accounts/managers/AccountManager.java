package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.AccountDAO;
import cl.ctl.accounts.model.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

    public Account createAccount(Account account) throws Exception {
        return accountDAO.createAccount(account);
    }

    public Account deleteAccount(long id) throws Exception {
        return accountDAO.deleteAccount(account);
    }


}
