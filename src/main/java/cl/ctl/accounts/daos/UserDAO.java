package cl.ctl.accounts.daos;

import cl.ctl.accounts.model.Account;
import cl.ctl.accounts.model.Client;
import cl.ctl.accounts.model.Holding;
import cl.ctl.accounts.model.User;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class UserDAO {

    static private final Logger logger = Logger.getLogger(UserDAO.class.getName());

    @Inject
    private ClientDAO clientDAO;

    @Inject
    private HoldingDAO holdingDAO;


    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;


    public User getUserByUsername(String username) throws Exception {

        User user = null;

        String sql = "{call ctl.get_user_by_username(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setString(1, username);

            call.execute();

            logger.log(Level.INFO, "Registros recuperados:");

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                user = createUserFromResultSet(rs);
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

        return user;
    }


    public User createUser(User user) throws Exception {

        String sql = "{call ctl.create_user(?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql);
        ) {

            call.setString(1, user.getUsername());
            call.setString(2, user.getPassword());
            call.setString(3, user.getSalt());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long id = rs.getLong(1);

                user.setId(id);

                return user;

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


    private User createUserFromResultSet(ResultSet resultSet) throws Exception {

        long id = resultSet.getLong("id");

        String username = resultSet.getString("username");
        String password = resultSet.getString("passwrd");
        String salt = resultSet.getString("salt");

        return new User(id, username, password, salt);
    }

}
