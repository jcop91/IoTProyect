package mx.iteso.iotproyect.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

//TODO(Clase) Se declara el modelo para uso de la variables correspondiente al usuario en realmDB.
public class UserDB extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String fullname;
    @Required
    private String email;
    private String SenderID;

    public UserDB(){}

    public UserDB(String Name, String Email, String senderID){
        this.fullname = Name;
        this.email = Email;
        this.SenderID =senderID;
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

    public String getEmail() { return email; }

    public String getSenderID() { return SenderID; }

}
