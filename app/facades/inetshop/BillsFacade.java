package facades.inetshop;

import com.avaje.ebean.Ebean;
import entities.Bill;
import entities.User;

import java.util.List;

public class BillsFacade {

    public List<Bill> getAllBills(User user){
        return Ebean.getDefaultServer().createQuery(Bill.class).where().eq("reciever", user).findList();
    }

    public byte[] getFileById(Integer id){
        return Ebean.getDefaultServer().createQuery(Bill.class).where().eq("id", id).findUnique().getFile();
    }
}
