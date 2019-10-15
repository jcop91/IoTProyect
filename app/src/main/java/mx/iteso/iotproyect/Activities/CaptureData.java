package mx.iteso.iotproyect.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import mx.iteso.iotproyect.R;

public class CaptureData extends AppCompatActivity{
    private EditText MultiText, EmailText;
    private ImageView btnFabMenuStart;
    private Button btnSaveInfo;
    private String id;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        MultiText = findViewById(R.id.setMultText);
        EmailText = findViewById(R.id.setEmailText);
        btnSaveInfo = findViewById(R.id.btnSaveData);
        btnFabMenuStart = findViewById(R.id.FabMenuStart);

        if(getIntent().getExtras() != null){
            id = getIntent().getExtras().getString("id");
            type = getIntent().getExtras().getInt("type");
        }else{
            MultiText.setHint("Ingresa tu nombre");
            btnFabMenuStart.setVisibility(View.INVISIBLE);
        }

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnFabMenuStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(id.isEmpty()){
            onDestroy();
        }
    }
}
