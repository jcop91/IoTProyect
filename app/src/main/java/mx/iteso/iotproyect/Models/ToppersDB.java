package mx.iteso.iotproyect.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

//TODO(Clase) Se declara el modelo para uso de la variables correspondiente a los toppers en realmDB.
public class ToppersDB extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String name;
    private int type;
    private int level;

    public ToppersDB(){}

    public ToppersDB(String ID, String Name, int Type){
        this.id = ID;
        this.name = Name;
        this.level = 0;
        this.type = Type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {return type;}

    public void setType(int type) {this.type = type;}
}
