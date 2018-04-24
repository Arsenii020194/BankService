package facades;

import com.avaje.ebean.Ebean;
import controllers.HomeController;
import entities.User;
import entities.UserData;
import play.mvc.Security;
import services.Secured;

import javax.inject.Inject;
import java.util.List;

import static services.Secured.LOGIN;

@Security.Authenticated(Secured.class)
public class UserDataFacade {
    @Inject
    private HomeController homeController;

    public User getUser() {
        String login = homeController.getCookie(LOGIN);
        List<User> usersList = Ebean.getDefaultServer().createQuery(User.class)
                .where()
                .eq("login", login)
                .findList();
        return usersList.get(0);
    }

    public UserData getUserData() {
        User user = getUser();
        if (user != null) {
            List<UserData> dataList = Ebean.getDefaultServer().createQuery(UserData.class)
                    .where()
                    .eq("user", user)
                    .findList();
            return dataList.get(0);
        } else {
            return null;
        }
    }
}
