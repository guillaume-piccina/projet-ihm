package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.DistanceNotif;
import projet.ihm.model.Profile;

import static projet.ihm.model.Application.PROFILE;

public class ParametersActivity extends AppCompatActivity {


    private Spinner NotifDistance ;
    private Spinner communautes ;
    Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.NotifDistance = findViewById(R.id.DistanceNotif);
        this.communautes = findViewById(R.id.communaute);

        String [] list = new String[3];
        for (int i=0 ; i<3 ; i++){
            list[i]=DistanceNotif.values()[i].toString();
        }

        NotifDistance.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,list
        ));

        communautes.setAdapter(new ArrayAdapter<Community>(getApplicationContext(),
                android.R.layout.simple_spinner_item,Community.values()));




        profile = getIntent().getParcelableExtra(PROFILE);

        if (profile!=null) {
            communautes.setSelection(getIndex(communautes,profile.getCOMMUNITY().toString()));
            NotifDistance.setSelection(getIndex(NotifDistance,profile.getDISTNOTIF().toString()));
        }



        // Bouton retour
        (findViewById(R.id.Retour)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            profile.setDISTNOTIF(NotifDistance.getSelectedItem().toString());
            profile.setCOMMUNITY( communautes.getSelectedItem().toString());
            intentSend.putExtra(PROFILE, (Parcelable) profile);
            startActivity(intentSend);
        });

        //Sélection distance Notification






        //Sélection Communauté






    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


}