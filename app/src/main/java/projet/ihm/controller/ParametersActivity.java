package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.DistanceNotif;

public class ParametersActivity extends AppCompatActivity {
    public final static String DISTANCE = "distance";
    public final static String COMMUNITY = "communaute";
    private Spinner distanceNotification;
    private Spinner community;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        this.distanceNotification = findViewById(R.id.DistanceNotif);
        this.community = findViewById(R.id.communaute);

        ArrayAdapter adapterDistance = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, DistanceNotif.values());
        distanceNotification.setAdapter(adapterDistance);
        selectSpinnerValue(distanceNotification, preferences.getString(DISTANCE, "50") + " m");

        ArrayAdapter adapterCommunity = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, Community.values());
        community.setAdapter(adapterCommunity);
        selectSpinnerValue(community, preferences.getString(COMMUNITY, "Tout le monde"));


        // Bouton retour
        (findViewById(R.id.Retour)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentSend);

            // Enregistrer les paramètres
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();

            Community communitySelected = (Community) community.getSelectedItem();
            DistanceNotif distanceSelected = (DistanceNotif) distanceNotification.getSelectedItem();

            editor.putString(COMMUNITY, communitySelected.toString());
            Integer distance = distanceSelected.getDistance();
            editor.putString(DISTANCE, distance.toString());

            editor.commit();

        });

    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

}