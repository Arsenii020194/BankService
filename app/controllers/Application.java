package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

public class Application extends Controller {

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.BillGenerationController.generatePdf()
                )
        ).as("text/javascript");
    }
}
