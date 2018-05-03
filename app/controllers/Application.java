package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

public class Application extends Controller {

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.BillGenerationController.generatePdf(),
                        routes.javascript.MainController.saveEdited(),
                        routes.javascript.BillGenerationController.download()
                )
        ).as("text/javascript");
    }
}
