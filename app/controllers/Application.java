package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

public class Application extends Controller {

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        controllers.inetshop.routes.javascript.BillGenerationController.generatePdf(),
                        controllers.inetshop.routes.javascript.MainController.saveEdited(),
                        controllers.inetshop.routes.javascript.BillGenerationController.download()
                )
        ).as("text/javascript");
    }
}
