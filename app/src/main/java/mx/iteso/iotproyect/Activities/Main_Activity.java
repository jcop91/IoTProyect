package mx.iteso.iotproyect.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import mx.iteso.iotproyect.Adapter.AlertAdapter;
import mx.iteso.iotproyect.Adapter.ListDataAdapter;
import mx.iteso.iotproyect.Services.Tools;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;
import mx.iteso.iotproyect.R;

public class Main_Activity extends AppCompatActivity implements RealmChangeListener<RealmResults<ToppersDB>>, AdapterView.OnItemClickListener {

    private boolean EmptyUserBD, EmptyTopperBD;
    private ListView ls_list;
    private ImageView iv_floatbtn;
    private ListDataAdapter adapter;
    private RealmResults<ToppersDB> toppers;
    private UserDB userDB;
    private Realm realm;
    private AlertDialog dialog;
    private String Msjbtn, Msj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.realm = Realm.getDefaultInstance();

        EmptyUserBD = Tools.isEmptyDB(realm, UserDB.class);
        EmptyTopperBD = Tools.isEmptyDB(realm, ToppersDB.class);

        if(EmptyUserBD){
            Intent intentEmptyData = new Intent(this,CaptureData.class);
            startActivity(intentEmptyData);
        }else if(EmptyTopperBD){

            Intent intent = new Intent(getApplicationContext(),CaptureData.class);
            intent.putExtra("type",1);
            startActivity(intent);
        }else{
            setContentView(R.layout.main_activity_list);
            this.ls_list = findViewById(R.id.DataList);
            this.iv_floatbtn = findViewById(R.id.FabMenu);

            toppers = realm.where(ToppersDB.class).findAll().sort("level", Sort.DESCENDING);
            toppers.addChangeListener(this);

            adapter = new ListDataAdapter(this,toppers);
            ls_list.setAdapter(adapter);
            ls_list.setOnItemClickListener(this);

            iv_floatbtn.setOnClickListener(addTopper);
        }
    }

    private View.OnClickListener addTopper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Msjbtn= "Agregar topper"; Msj="¿Desea agregar nuevo topper?";
            Drawable draw = getResources().getDrawable(android.R.drawable.ic_input_add,null);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),CaptureData.class);
                    intent.putExtra("type",1);
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            showAlertForAnyActions(thread,draw);
        }
    };

    private void showAlertForAnyActions(Thread proccess, Drawable drawable){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertAdapter adapter = new AlertAdapter(this,Msjbtn,Msj, drawable);
        builder.setView(adapter.getView(null));
        adapter.setThread(proccess);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onChange(RealmResults<ToppersDB> toppers) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        Msjbtn= "Editar topper"; Msj="¿Desea editar el topper '"+toppers.get(position).getName()+"'?";
        final String idString = toppers.get(position).getId();
        Drawable draw = getResources().getDrawable(android.R.drawable.ic_menu_edit,null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),CaptureData.class);
                intent.putExtra("id",idString);
                intent.putExtra("type",2);
                startActivity(intent);
                dialog.cancel();
            }
        });
        showAlertForAnyActions(thread,draw);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }
}
