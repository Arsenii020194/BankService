package controllers;

import entities.UserData;
import facades.UserDataFacade;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.Secured;

import javax.inject.Inject;

@Security.Authenticated(Secured.class)
public class MainController extends Controller {
    @Inject
    private UserDataFacade userDataFacade;
    @Inject
    private FormFactory factory;

    public Result info() {
        UserData data = userDataFacade.getUserData();

        if (data != null) {
            return ok(views.html.info.render(data));
        }
        return notFound();
    }

    public Result generate(){
        return ok(views.html.bill_generation.render());
    }
}
