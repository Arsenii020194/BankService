package facades;

import com.avaje.ebean.Ebean;
import controllers.HomeController;
import entities.User;
import play.mvc.Security;
import services.Secured;

import javax.inject.Inject;
import java.util.List;

import static services.Secured.LOGIN;

public class AuthFacade {

    public boolean isContainsInDb(String login, String password) {
        List<User> usersList = Ebean.getDefaultServer().createQuery(User.class)
                .where()
                .eq("login", login)
                .and()
                .eq("password", password).findList();
        if (usersList.size() > 1 || usersList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public User getUser(String login, String password){
        List<User> usersList = Ebean.getDefaultServer().createQuery(User.class)
                .where()
                .eq("login", login)
                .and()
                .eq("password", password).findList();
        return usersList.get(0);
    }
}
