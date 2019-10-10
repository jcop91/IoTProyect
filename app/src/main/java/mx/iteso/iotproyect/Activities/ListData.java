package mx.iteso.iotproyect.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import mx.iteso.iotproyect.Adapter.AlertAdapter;
import mx.iteso.iotproyect.Adapter.ListDataAdapter;
import mx.iteso.iotproyect.Models.Toppers;
import mx.iteso.iotproyect.R;

public class ListData extends AppCompatActivity implements RealmChangeListener<RealmResults<Toppers>>, AdapterView.OnItemClickListener {

    private ListView ls_list;
    private ImageView iv_floatbtn;
    private ListDataAdapter adapter;
    private RealmResults<Toppers> toppers;
    private Realm realm;
    private AlertDialog dialog;
    private String Msjbtn, Msj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_list);
        this.ls_list = findViewById(R.id.DataList);
        this.iv_floatbtn = findViewById(R.id.FabMenu);

        this.realm = Realm.getDefaultInstance();
        toppers = realm.where(Toppers.class).findAll();
        toppers.addChangeListener(this);

        adapter = new ListDataAdapter(this,toppers);
        ls_list.setAdapter(adapter);

        iv_floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Msjbtn= "Agregar topper"; Msj="¿Desea agregar nuevo topper?";
                Drawable draw = getResources().getDrawable(android.R.drawable.ic_input_add,null);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),CaptureData.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
               showAlertForAnyActions(thread,draw);
            }
        });
    }

    private void showAlertForAnyActions(Thread proccess, Drawable drawable){
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertAdapter adapter = new AlertAdapter(this,Msjbtn,Msj, drawable);
        builder.setView(adapter.getView(null));
        adapter.setThread(proccess);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onChange(RealmResults<Toppers> toppers) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        Msjbtn= "Editar topper"; Msj="¿Desea editar el topper '"+toppers.get(position).getName()+"'?";
        Drawable draw = getResources().getDrawable(android.R.drawable.ic_menu_edit,null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),CaptureData.class);
                intent.putExtra("id", toppers.get(position).getId());
                startActivity(intent);
                dialog.cancel();
            }
        });
        showAlertForAnyActions(thread,draw);
    }
}
