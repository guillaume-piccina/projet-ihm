package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.DistanceNotif;

public class ParametersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Bouton retour
        (findViewById(R.id.Retour)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentSend);
        });

        //Sélection distance Notification

        Spinner NotifDistance = findViewById(R.id.DistanceNotif);
        String [] list = new String[3];
        for (int i=0 ; i<3 ; i++){
            list[i]=DistanceNotif.values()[i].toString();
        }

        NotifDistance.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,list
                ));
    }
}