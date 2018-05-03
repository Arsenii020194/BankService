package entities;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends Model {
    @Column(name = "id_user")
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private UserData userData;

    @Column
    private String login;

    @Column
    private Integer role;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reciever")
    private List<Bill> bills;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userData, user.userData) &&
                Objects.equals(login, user.login) &&
                Objects.equals(role, user.role) &&
                Objects.equals(password, user.password) &&
                Objects.equals(bills, user.bills);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userData, login, role, password, bills);
    }
}
