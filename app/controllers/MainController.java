package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.AccountDTO;
import dto.SavingDataDTO;
import entities.Bank;
import entities.UserData;
import facades.AccountsFacade;
import facades.BankFacade;
import facades.UserDataFacade;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.Secured;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;

@Security.Authenticated(Secured.class)
public class MainController extends Controller {
    @Inject
    private UserDataFacade userDataFacade;
    @Inject
    private FormFactory factory;
    @Inject
    private BankFacade bankFacade;
    @Inject
    private AccountsFacade accountsFacade;

    public Result info() {
        UserData data = userDataFacade.getUserData();

        if (data != null) {
            return ok(views.html.info.render(data));
        }
        return notFound();
    }

    public Result admin() {
        UserData data = userDataFacade.getUserData();

        if (data != null) {
            return ok(views.html.info.render(data));
        }
        return notFound();
    }

    public Result generate() {
        return ok(views.html.bill_generation.render());
    }

    public Result edit() {
        UserData data = userDataFacade.getUserData();
        List<Bank> banks = bankFacade.getBanks();
        if (data != null) {
            return ok(views.html.edit.render(data, banks));
        }
        return notFound();
    }

    public Result saveEdited() {
        String dto = request().body().asFormUrlEncoded().get("data")[0];

        Type savingDataDTOType = new TypeToken<SavingDataDTO>() {
        }.getType();

        SavingDataDTO savingDataDTO = new Gson().fromJson(dto, savingDataDTOType);

        UserData userData = userDataFacade.getUserData();

        if (!savingDataDTO.getOrgData().equals(userData)) {
            userDataFacade.updateUserData(savingDataDTO.getOrgData());
        }

        for (AccountDTO accDto : savingDataDTO.getNewAccounts()) {
            accountsFacade.saveAccDTO(accDto);
        }

        for (AccountDTO accDto : savingDataDTO.getAccountsToDelete()) {
            accountsFacade.deleteAccountDTO(accDto);
        }

        return ok();
    }
}
