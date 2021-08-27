package cl.ctl.accounts.managers;

import cl.ctl.accounts.daos.AccountDAO;
import cl.ctl.accounts.daos.UserDAO;
import cl.ctl.accounts.model.Account;
import cl.ctl.accounts.model.User;
import cl.ctl.accounts.util.PasswordUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class UserManager {

    @Inject
    private UserDAO userDAO;

    public User authenticateUser(User user) throws Exception {
        User persistedUser = userDAO.getUserByUsername(user.getUsername());

        if(persistedUser == null) {
            throw new Exception("No existe el usuario " + user.getUsername());
        }

        // User provided password to validate
        String providedPassword = user.getPassword();

        // Encrypted and Base64 encoded password read from database
        String securePassword = persistedUser.getPassword();

        // Salt value stored in database
        String salt = persistedUser.getSalt();

        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);

        if(passwordMatch)
        {
            return persistedUser;
        } else {
            throw new AuthenticationException("Usuario y/o contraseña incorrectos");
        }
    }

    public User createUser(User user) throws Exception {

        String myPassword = user.getPassword();

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);

        // Print out protected password
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);

        user.setPassword(mySecurePassword);
        user.setSalt(salt);

        return userDAO.createUser(user);
    }


}
