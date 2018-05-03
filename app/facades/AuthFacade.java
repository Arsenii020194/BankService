package facades;

import com.avaje.ebean.Ebean;
import controllers.HomeController;
import entities.User;
import entities.UserData;

import javax.inject.Inject;
import java.util.List;

public class AuthFacade {
    @Inject
    private HomeController homeController;

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

    public UserData findByLogin(String login) {
        List<User> usersList = Ebean.getDefaultServer().createQuery(User.class)
                .where()
                .eq("login", login)
                .findList();
        return usersList.get(0).getUserData();
    }

    public User getUser(String login, String password) {
        List<User> usersList = Ebean.getDefaultServer().createQuery(User.class)
                .where()
                .eq("login", login)
                .and()
                .eq("password", password).findList();
        return usersList.get(0);
    }
}
