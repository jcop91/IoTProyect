package mx.iteso.iotproyect.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import io.realm.Realm;
import mx.iteso.iotproyect.Models.StringsClass;
import mx.iteso.iotproyect.Models.UserRequest;
import mx.iteso.iotproyect.Services.FirebaseInstanceIDStartService;
import mx.iteso.iotproyect.Services.Tools;
import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.R;

public class CaptureData extends AppCompatActivity{
    private EditText MultiText, EmailText;
    private Button btnSaveInfo;
    private ToppersDB toppersDB;
    private Realm realm;
    private String id, QRcode, senderId;
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        MultiText = findViewById(R.id.setMultText);
        EmailText = findViewById(R.id.setEmailText);
        btnSaveInfo = findViewById(R.id.btnSaveData);

        //Recupera los datos de la instancia de base de datos local
        this.realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            //Valida que datos se van a capturar o actualizar
            type = getIntent().getExtras().getInt(StringsClass.typeField);
            switch (type){
                case 1:
                    //Se ejecuta esta funcion para llamar a la libreria encargada de capturar el codigo Qr
                    new IntentIntegrator(this)
                            .setPrompt(StringsClass.MessageQrScan)
                            .setOrientationLocked(true)
                            .initiateScan();
                    break;
                case 2:
                    //Actualiza el nombre del topper
                    StyleCaseThisActivity(type);

                    id = getIntent().getExtras().getString(StringsClass.idField);
                    toppersDB = realm.where(ToppersDB.class).equalTo(
                            StringsClass.idField,id).findFirst();
                    MultiText.setText(toppersDB.getName());

                    break;
            }
        }else{
            StyleCaseThisActivity(type);
        }

        //Se ejecuta despues de oprimir el boton de guardar
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case 0:
                        if(!TextUtils.isEmpty(EmailText.getText().toString()) &&
                                !TextUtils.isEmpty(MultiText.getText().toString())){
                            if(Tools.isEmailValid(EmailText.getText().toString())){

                                senderId = FirebaseInstanceIDStartService.getToken();

                                UserRequest newUserRequest = new UserRequest(
                                        MultiText.getText().toString(),
                                        EmailText.getText().toString(),
                                        senderId);

                                Tools.sendNetworkRequestNewUser(CaptureData.this,newUserRequest,realm);
                            }
                            else
                                Toast.makeText(CaptureData.this,StringsClass.MessageEmailError,Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(CaptureData.this,StringsClass.MessageFieldEmpty,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        String [] data = QRcode.split("-");
                        if(!Tools.existsTopper(realm, ToppersDB.class,data[0])){
                            if(!TextUtils.isEmpty(EmailText.getText().toString())){

                                toppersDB = new ToppersDB(
                                        data[0],EmailText.getText().toString(),
                                        Integer.parseInt(data[1]));
                                Tools.sendNetworkRequestChangeTopperName(CaptureData.this,type,realm,toppersDB);
                            }
                            else
                                Toast.makeText(CaptureData.this,StringsClass.MessageGetDataError,Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(CaptureData.this,StringsClass.MessageTopperIsNotNew,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        toppersDB.setName(MultiText.getText().toString());
                        Tools.sendNetworkRequestChangeTopperName(CaptureData.this,type,realm,toppersDB);
                        break;
                        default:
                            Toast.makeText(CaptureData.this,StringsClass.MessageGetDataError,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO(Metodo): Se ejecuta posterior a capturar el codigo Qr
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            StyleCaseThisActivity(type);
            QRcode = result.getContents();
            MultiText.setText(QRcode);

        }else{
            Toast.makeText(this,StringsClass.MessageQrScanError,Toast.LENGTH_SHORT).show();
        }
    }

    //TODO(Metodo): Se ejecuta cuando se oprime el boton de retroceso
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,Main_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //TODO(Metodo): Se utiliza para asignar atributos de diseño de los campos en esta ventana.
    private void StyleCaseThisActivity(int eventType){
        //General diseño
        MultiText.setInputType(InputType.TYPE_CLASS_TEXT);
        EmailText.setInputType(InputType.TYPE_CLASS_TEXT);

        MultiText.setGravity(Gravity.CENTER);
        EmailText.setGravity(Gravity.CENTER);

        MultiText.setTextSize((float) 14);
        EmailText.setTextSize((float) 14);

        switch (eventType){
           case 0: //Se utiliza cuando se captura el usuario y correo
               EmailText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
               EmailText.setVisibility(View.VISIBLE);
               EmailText.setHint(StringsClass.MessageHitTextField1Case0);
               MultiText.setHint(StringsClass.MessageHitTextField2Case0);
               btnSaveInfo.setText(StringsClass.titleButtonSaveCase0);
               break;
           case 1:  //Se utiliza despues de capturar el qr
               EmailText.setVisibility(View.VISIBLE);
               EmailText.setText("");
               EmailText.setHint(StringsClass.MessageHitTextField2Case1);
               MultiText.setEnabled(false);
               btnSaveInfo.setText(StringsClass.titleButtonSaveCase1);
               break;
           case 2://Se utiliza cuando se desea modificar el nombre
               btnSaveInfo.setText(StringsClass.titleButtonSaveCase2);
               EmailText.setVisibility(View.INVISIBLE);
               break;
       }
    }
}