package cl.ctl.password.daos;

import cl.ctl.password.model.Client;
import cl.ctl.password.model.Holding;

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
public class ClientDAO {

    static private final Logger logger = Logger.getLogger(HoldingDAO.class.getName());

    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;

    public Client getClientById(long id) throws Exception {

        Client client = null;

        String sql = "{call ctl.get_client_by_id(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, id);

            call.execute();

            logger.log(Level.INFO, "Registros recuperados:");

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                client = createClientFromResultSet(rs);
            }
            else {
                String errorMsg = "Error al recuperar el holding de la BDD.";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return client;
    }



    private Client createClientFromResultSet(ResultSet resultSet) throws Exception {

        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Client(id, name);
    }

}
