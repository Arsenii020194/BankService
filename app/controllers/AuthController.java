package controllers;

import entities.User;
import facades.AuthFacade;
import forms.LoginUser;
import play.Logger;
import play.api.cache.CacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.Secured;
import views.html.login;

import javax.inject.Inject;
import java.sql.SQLException;

import static services.Secured.USER_TIME;

public class AuthController extends Controller {
    @Inject
    private FormFactory formFactory;
    @Inject
    private AuthFacade authFacade;
    @Inject
    private CacheApi cache;
    @Inject
    private HomeController main;

    public Result validateAutentification() {
        Form<LoginUser> loginForm = formFactory.form(LoginUser.class).bindFromRequest();
        if (loginForm.hasErrors()) return badRequest(login.render("", loginForm));

        String login = loginForm.data().get("login");
        String pass = loginForm.data().get("password");
        User user = null;
        if (authFacade.isContainsInDb(login, pass)) {
            user = authFacade.getUser(login, pass);
            userSessionStart(user);

        } else {
            loginForm.reject("user not found");
            return badRequest(views.html.login.render("", loginForm));
        }
        return main.GO_MAIN;
    }

    public Result login() {
        try {
            org.h2.tools.Console.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if  (userSessionCheck()){
            return main.GO_MAIN;
        }
        Form<LoginUser> userForm = formFactory.form(LoginUser.class);
        return ok(login.render("", userForm));
    }

    /**
     * Создание пользовательской сессии в системе
     */
    private boolean userSessionStart(User user) {
        try {
            main.createCookie("login", user.getLogin());
            main.createCookie("role", user.getRole().toString());
            return true;
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Завершение пользовательской сессии в системе
     */
    public boolean userSessionStop() {
        try {
            cache.remove(main.getCookie("login"));
            cache.remove(main.getCookie("role"));
            main.removeCookie("login");
            main.removeCookie("role");
            main.removeCookie(USER_TIME);
            return true;

        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Проверка, что пользователь залогинен
     */
    public boolean userSessionCheck() {
        try {
            return (main.getCookie("login") != null && main.getCookie("login") != null);
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Выход из системы
     */
    @Security.Authenticated(Secured.class)
    public Result logout() {
        userSessionStop();
        return main.GO_LOGIN;
    }

}
