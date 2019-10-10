package mx.iteso.iotproyect.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String fullname;
    @Required
    private String email;

    private RealmList<Toppers> toppers;

    public User(){}

    public User(String Name, String Email){
        this.id = "0";
        this.fullname = Name;
        this.toppers = new RealmList<Toppers>();
        this.email = Email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String name) {
        this.fullname = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public RealmList<Toppers> getToppers() { return toppers; }
}
