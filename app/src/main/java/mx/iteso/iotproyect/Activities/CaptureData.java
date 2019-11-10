package mx.iteso.iotproyect.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.realm.Realm;
import mx.iteso.iotproyect.Models.Tools;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.Models.UserDB;
import mx.iteso.iotproyect.Models.UserRequest;
import mx.iteso.iotproyect.R;

public class CaptureData extends AppCompatActivity{
    private EditText MultiText, EmailText;
    private Button btnSaveInfo;
    private ToppersDB toppersDB;
    private Realm realm;
    private String id, QRcode;
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        MultiText = findViewById(R.id.setMultText);
        EmailText = findViewById(R.id.setEmailText);
        btnSaveInfo = findViewById(R.id.btnSaveData);

        this.realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            type = getIntent().getExtras().getInt("type");
            switch (type){
                case 1:
                    new IntentIntegrator(this)
                            .setPrompt(this.getString(R.string.scan_qr_code))
                            .setOrientationLocked(true)
                            .initiateScan();
                    break;
                case 2:
                    id = getIntent().getExtras().getString("id");
                    toppersDB = realm.where(ToppersDB.class).equalTo("id",id).findFirst();
                    MultiText.setText(toppersDB.getName());
                    MultiText.setInputType(InputType.TYPE_CLASS_TEXT);
                    MultiText.setGravity(Gravity.CENTER);
                    btnSaveInfo.setText("Guardar Nombre");
                    EmailText.setVisibility(View.INVISIBLE);
                    break;
            }
        }else{
            EmailText.setVisibility(View.VISIBLE);
            MultiText.setHint("Ingresa tu nombre");
            EmailText.setHint("Ingresa tu correo");
            MultiText.setInputType(InputType.TYPE_CLASS_TEXT);
            EmailText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            btnSaveInfo.setText("Guardar datos");
        }

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case 0:
                        boolean validEmail = Tools.isEmailValid(EmailText.getText().toString());
                        boolean validName = Tools.isnotEmptyValid(MultiText.getText().toString());

                        if(validEmail || validName){
                            if(validName){
                                if(validEmail){
                                    UserRequest newUserRequest = new UserRequest(MultiText.getText().toString(),EmailText.getText().toString());
                                    UserDB newUserDB = new UserDB(newUserRequest.getFullname(),newUserRequest.getEmail());
                                    Tools.sendNetworkRequestNewUser(CaptureData.this,newUserRequest);

                                    newUserDB.setId(newUserRequest.getId());
                                   // realm.beginTransaction();
                                    //realm.copyToRealm(newUserDB);
                                    //realm.commitTransaction();
                                }else
                                    Toast.makeText(CaptureData.this,"Agrege correo correcto",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(CaptureData.this,"Agrege nombre",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(CaptureData.this,"Agrege nombre y correo",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        String [] data = QRcode.split("-");
                        boolean topperIsExists = Tools.existsTopper(realm, ToppersDB.class,data[0]);
                        if(topperIsExists){
                            Toast.makeText(CaptureData.this,"El topper ya existe",Toast.LENGTH_SHORT).show();
                        }else{
                            boolean IsnotEmpty = Tools.isnotEmptyValid(EmailText.getText().toString());
                            if(IsnotEmpty){
                                realm.beginTransaction();
                                toppersDB = new ToppersDB(data[0],EmailText.getText().toString(),Integer.parseInt(data[1]));
                                realm.copyToRealm(toppersDB);
                                realm.commitTransaction();
                            }
                            else
                                Toast.makeText(CaptureData.this,"Agregar un topper correcto",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        realm.beginTransaction();
                        toppersDB.setName(MultiText.getText().toString());
                        realm.copyToRealmOrUpdate(toppersDB);
                        realm.commitTransaction();
                        break;
                        default:
                            Toast.makeText(CaptureData.this,"Error al capturar los datos",Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(CaptureData.this,Main_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            QRcode = result.getContents();
            MultiText.setEnabled(false);
            MultiText.setText(QRcode);
            MultiText.setTextSize((float) 14);
            MultiText.setGravity(Gravity.CENTER);
            EmailText.setVisibility(View.VISIBLE);
            EmailText.setText("");
            EmailText.setHint("Ingrese el nombre del topper");
            EmailText.setInputType(InputType.TYPE_CLASS_TEXT);
            btnSaveInfo.setText("Guardar Topper");
        }else{
            Toast.makeText(this,"Error al capturar el código QR",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CaptureData.this,Main_Activity.class);
        startActivity(intent);
    }
}
