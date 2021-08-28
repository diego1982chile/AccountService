package cl.ctl.accounts.daos;

import cl.ctl.accounts.model.Client;
import cl.ctl.accounts.model.Holding;

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
public class HoldingDAO {

    static private final Logger logger = Logger.getLogger(HoldingDAO.class.getName());

    @Resource(lookup = "java:global/accountsDS")
    private DataSource dataSource;

    public Holding getHoldingById(long id) throws Exception {

        Holding holding = null;

        String sql = "select * from holding where id = ?";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            call.setLong(1, id);

            ResultSet rs = call.executeQuery();

            logger.log(Level.INFO, "Registros recuperados:");

            if (rs.next()) {
                holding = createHoldingFromResultSet(rs);
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

        return holding;
    }


    public List<Holding> getAllHoldings() throws Exception {

        List<Holding> holdings = new ArrayList<>();

        String sql = "select * from holding";

        try (Connection connect = dataSource.getConnection();
             PreparedStatement call = connect.prepareStatement(sql)) {

            ResultSet rs = call.executeQuery();

            logger.log(Level.INFO, "Registros recuperadas:");

            while (rs.next()) {
                holdings.add(createHoldingFromResultSet(rs));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return holdings;
    }


    private Holding createHoldingFromResultSet(ResultSet resultSet) throws Exception {

        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Holding(id, name);
    }

}
