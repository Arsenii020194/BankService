package services;


import controllers.AuthController;
import controllers.HomeController;
import play.Configuration;
import play.api.cache.CacheApi;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Date;

public class Secured extends Security.Authenticator {
    @Inject
    private CacheApi cache;
    @Inject
    private HomeController main;
    @Inject
    private AuthController auth;
    public static final String LOGIN = "login";
    public static final String USER_TIME = "userTime";
    public static final String MAX_AGE_PATH = "play.http.session.maxAge";

    @Override
    public String getUsername(Http.Context ctx) {
        // see if user is logged in
        String login = main.getCookie(LOGIN);
        if (login == null) return null;

// see if the session is expired
        String previousTick = main.getCookie(USER_TIME);
        if (previousTick != null && !previousTick.equals("")) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(Configuration.root().getString(MAX_AGE_PATH));
            if ((currentT - previousT)/1000 > timeout) {
// session expired
                auth.userSessionStop();
                return null;
            }
        }
        // update time in session
        String tickString = Long.toString(new Date().getTime());
        main.createCookie(USER_TIME, tickString);
        return login;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return main.GO_LOGIN;
    }
}
