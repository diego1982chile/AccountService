package cl.ctl.accounts.daos;

import cl.ctl.accounts.model.Account;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class DatabaseDAO {

    static private final Logger logger = Logger.getLogger(DatabaseDAO.class.getName());

    private static final String SCRIPTS_PATH = "/cl/ctl/accounts/scripts/";


    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;


    public void executeScript(String script) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(SCRIPTS_PATH + script)));
        String line = "";

        String sql = "";

        while ((line = reader.readLine()) != null) {
            if(line.trim().isEmpty()) {
                continue;
            }
            sql = sql + line;
        }
        reader.close();

        try (Connection connect = dataSource.getConnection();
             Statement statement = connect.createStatement()) {

            statement.executeUpdate(sql);

            logger.log(Level.INFO, "Script " + script + " ejecutado exitosamente");

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    public void populateTableClient() throws Exception {

        String sql = "INSERT INTO client('name') VALUES (?)";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            call.setString(1, "nutrisa");
            call.addBatch();

            call.setString(1, "legrand");
            call.addBatch();

            call.setString(1, "bless");
            call.addBatch();

            call.setString(1, "soho");
            call.addBatch();

            // submit the batch for execution
            call.executeBatch();

            logger.log(Level.INFO, "Tabla 'client' poblada exitosamente");


        } catch (SQLException e) {
            String errorMsg = "Error SQL";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    public void populateTableHolding() throws Exception {

        String sql = "INSERT INTO holding('name') VALUES (?)";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            call.setString(1, "construmart");
            call.addBatch();

            call.setString(1, "easy");
            call.addBatch();

            call.setString(1, "sodimac");
            call.addBatch();

            call.setString(1, "cencosud");
            call.addBatch();

            call.setString(1, "smu");
            call.addBatch();

            call.setString(1, "tottus");
            call.addBatch();

            call.setString(1, "walmart");
            call.addBatch();

            // submit the batch for execution
            call.executeBatch();

            logger.log(Level.INFO, "Tabla 'holding' poblada exitosamente");


        } catch (SQLException e) {
            String errorMsg = "Error SQL";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }


}
