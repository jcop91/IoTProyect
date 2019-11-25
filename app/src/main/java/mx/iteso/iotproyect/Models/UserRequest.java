package mx.iteso.iotproyect.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//TODO(Clase) Se declara el modelo para uso de la variables correspondiente al usuario en retrofit.
public class UserRequest implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("senderID")
    private String SenderID;

    public UserRequest(String fullname, String email, String senderID) {
        this.fullname = fullname;
        this.email = email;
        this.SenderID = senderID;
    }

    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getSenderID() {
        return SenderID;
    }
}
