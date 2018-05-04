package controllers;

import jsmessages.JsMessages;
import jsmessages.JsMessagesFactory;
import jsmessages.japi.Helper;
import play.Logger;
import play.api.cache.CacheApi;
import play.api.i18n.MessagesApi;
import play.data.FormFactory;
import play.libs.Scala;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final JsMessages jsMessages;

    @Inject
    public HomeController(JsMessagesFactory jsMessagesFactory) {
        jsMessages = jsMessagesFactory.all();
    }

    @Inject
    private FormFactory formFactory;
    @Inject
    private CacheApi cache;
    @Inject
    private MessagesApi messages;
    @Inject
    private AuthController auth;

    public Result GO_LOGIN = redirect(
            routes.AuthController.login()
    );

    public Result GO_MAIN = redirect(controllers.inetshop.routes.MainController.info());
    public Result GO_MAIN_ADMIN = redirect(controllers.inetshop.routes.MainController.admin());


    /**
     * Сохранить cookie
     */
    public void createCookie(String name, String data) {
// response().setCookie(Http.Cookie.builder(name, data).build());
        try {
            response().setCookie(Http.Cookie.builder(name, URLEncoder.encode(data, "UTF-8")).build());
        } catch (UnsupportedEncodingException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    /**
     * Получить cookie
     */
    public String getCookie(String name) {
        Http.Cookie cookie = null;
        Optional<Http.Cookie> opt = response().cookie(name);
        if (opt != null && opt.isPresent()) cookie = opt.get();
        if (cookie == null) {
            cookie = request().cookies().get(name);
        }
        try {
            return (cookie != null) ? URLDecoder.decode(cookie.value(), "UTF-8") : null;
        } catch (UnsupportedEncodingException e) {
            Logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Удалить cookie
     */
    public void removeCookie(String name) {
        response().discardCookie(name);
    }

    /**
     * Скачать файл (с URLEncoder, нужно обрабатывать название файла!)
     */
    public Result download(byte[] file, String fileName, String fileFormat) throws UnsupportedEncodingException {
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", " ");
        response().setHeader("Cache-Control", "public");
        response().setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ((!Objects.equals(fileFormat, "")) ? "." + fileFormat : "") + "\"");
        response().setHeader("Content-Type", "application/octet-stream");
        response().setHeader("Content-Transfer-Encoding", "binary");
        return ok(file);
    }

    public Result download(byte[] file, String fileName) throws UnsupportedEncodingException {
        return download(file, fileName, "");
    }

    /**
     * Следующая функция необходима для передачи сообщений (conf/messages) в файлы JavaScript
     */
    public Result jsMessages() {
        return ok(jsMessages.apply(Scala.Option("window.Messages"), Helper.messagesFromCurrentHttpContext()));
    }

    /**
     * Данная функция нужна для передачи Javascript параметорв в методы контроллеров
     */
    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes"
                )
        ).as(Http.MimeTypes.JAVASCRIPT);
    }

    /**
     * Изменить язык приложения для клиента
     */
    public Result changeLanguage(String lang) {
        ctx().changeLang(lang);
        return redirect(request().getHeader(Http.HeaderNames.REFERER));
    }
}
