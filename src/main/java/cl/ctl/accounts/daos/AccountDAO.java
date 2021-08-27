package cl.ctl.accounts.daos;

import cl.ctl.accounts.model.Account;
import cl.ctl.accounts.model.Client;
import cl.ctl.accounts.model.Holding;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class AccountDAO {

    static private final Logger logger = Logger.getLogger(AccountDAO.class.getName());

    @Inject
    private ClientDAO clientDAO;

    @Inject
    private HoldingDAO holdingDAO;


    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;


    public Account getAccountById(long id) throws Exception {

        Account account = null;

        String sql = "{call ctl.get_account_by_id(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, id);

            call.execute();

            logger.log(Level.INFO, "Registros recuperados:");

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                account = createAccountFromResultSet(rs);
            }
            else {
                String errorMsg = "Error al recuperar la descripción de la BDD.";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return account;
    }

    public Account getAccountByClientAndHolding(String clientName, String holdingName) throws Exception {

        Account account = null;

        String sql = "{call ctl.get_account_by_client_and_holding(?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setString(1, clientName);
            call.setString(2, holdingName);

            call.execute();

            logger.log(Level.INFO, "Registros recuperados:");

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                account = createAccountFromResultSet(rs);
            }
            else {
                String errorMsg = "Error al recuperar la descripción de la BDD.";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return account;
    }

    public Account createAccount(Account account) throws Exception {

        String sql = "{call ctl.create_account(?,?,?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql);
        ) {

            call.setLong(1, account.getClient().getId());
            call.setLong(2, account.getHolding().getId());
            call.setString(3, account.getUser());
            call.setString(4, account.getPassword());
            call.setString(5, account.getCompany());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long id = rs.getLong(1);

                account.setId(id);

                return account;

            } else {
                connect.rollback();
                String errorMsg = "El registro no fue creado. Contacte a Desarrollo";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }
            //rs.close();
            //connect.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

    }


    public Account updateAccount(Account account) throws Exception {

        String sql = "{call ctl.update_account(?,?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql);
        ) {

            call.setLong(1, account.getId());
            call.setString(2, account.getUser());
            call.setString(3, account.getPassword());
            call.setString(4, account.getCompany());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long id = rs.getLong(1);

                return getAccountById(account.getId());

            } else {
                connect.rollback();
                String errorMsg = "El registro no fue creado. Contacte a Desarrollo";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }
            //rs.close();
            //connect.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

    }

    public long deleteAccount(long id) throws Exception {

        String sql = "{call ctl.delete_account(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql);
        ) {

            call.setLong(1, id);

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long idDeleted = rs.getLong(1);

                if(idDeleted < 0) {
                    connect.rollback();
                    String errorMsg = "El registro no fue eliminado. Contacte a Desarrollo";
                    logger.log(Level.SEVERE, errorMsg);
                    throw new Exception(errorMsg);
                }

                return idDeleted;

            } else {
                connect.rollback();
                String errorMsg = "El registro no fue eliminado. Contacte a Desarrollo";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }
            //rs.close();
            //connect.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

    }

    public List<Account> getAllAccounts() throws Exception {

        List<Account> accounts = new ArrayList<>();

        String sql = "{call ctl.get_all_accounts()}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.execute();

            logger.log(Level.INFO, "Registros recuperadas:");

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                accounts.add(createAccountFromResultSet(rs));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return accounts;
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws Exception {

        long id = resultSet.getLong("id");

        String company = resultSet.getString("company");
        String user = resultSet.getString("username");
        String password = resultSet.getString("userpass");

        Client client = clientDAO.getClientById(resultSet.getLong("id_client"));

        Holding holding = holdingDAO.getHoldingById(resultSet.getLong("id_holding"));

        return new Account(id, company, user, password, client, holding);
    }

}
