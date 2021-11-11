package cl.ctl.accounts.daos;

import cl.ctl.accounts.model.Client;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
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
public class ClientDAO {

    static private final Logger logger = Logger.getLogger(HoldingDAO.class.getName());

    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;

    public List<Client> getAllClients() throws Exception {

        List<Client> clients = new ArrayList<>();

        String sql = "select * from client order by name";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            ResultSet rs = call.executeQuery();

            logger.log(Level.INFO, "Registros recuperadas:");

            while (rs.next()) {
                clients.add(createClientFromResultSet(rs));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return clients;
    }

    public Client getClientById(long id) throws Exception {

        Client client = null;

        String sql = "select * from client where id = ?";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            call.setLong(1, id);

            ResultSet rs = call.executeQuery();

            logger.log(Level.INFO, "Registros recuperados:");

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

    public Client createClient(Client client) throws Exception {

        String sql = "INSERT INTO client('name') VALUES (?) RETURNING id";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql);
        ) {

            call.setString(1, client.getName());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long id = rs.getLong(1);

                client.setId(id);

                return client;

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

    public Client updateClient(Client client) throws Exception {

        String sql = "UPDATE client SET name = ? WHERE id = ? RETURNING id";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql);
        ) {

            call.setString(1, client.getName());
            call.setLong(2, client.getId());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                long id = rs.getLong(1);

                return getClientById(client.getId());

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

    public long deleteClient(long id) throws Exception {

        String sql = "DELETE FROM client WHERE id = ? RETURNING id";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql);
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



    private Client createClientFromResultSet(ResultSet resultSet) throws Exception {

        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Client(id, name);
    }

}
