package mx.iteso.iotproyect.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mx.iteso.iotproyect.Aplication.RunApp;

public class Main_Activity extends AppCompatActivity {

    private boolean EmptyBD = RunApp.EmptyBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!EmptyBD){
            Intent intentEmptyData = new Intent(this,CaptureData.class);
            startActivity(intentEmptyData);
        }else{
            Intent intentEmptyData = new Intent(this,ListData.class);
            startActivity(intentEmptyData);
        }

    }
}
