package mx.iteso.iotproyect.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopperRequest implements Serializable {

    @SerializedName("id")
    private String Id;
    @SerializedName("name")
    private String Name;
    @SerializedName("type")
    private int Type;
    @SerializedName("level")
    private int Level;
    @SerializedName("userId")
    private String UserId;

    public TopperRequest(){}

    public TopperRequest(String id,String name, int type, int level,String userId){
        this.Id =id;
        this.Name = name;
        this.Type = type;
        this.Level = level;
        this.UserId = userId;
    }

    public String getId() { return Id; }

    public String getName() { return Name; }

    public int getType() { return Type; }

    public int getLevel() { return Level; }

    public String getUserId() { return UserId; }

}
