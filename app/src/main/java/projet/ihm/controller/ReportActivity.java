package projet.ihm.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import projet.ihm.model.Position;
import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.Factory;
import projet.ihm.model.FactorySimple;
import projet.ihm.model.Profile;
import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.TypeIncident;

import static projet.ihm.model.Application.LATITUDE;
import static projet.ihm.model.Application.LONGITUDE;
import static projet.ihm.model.Application.PROFILE;
import static projet.ihm.model.incident.Incident.INCIDENT;

public class ReportActivity extends AppCompatActivity {
    private RadioGroup firstRadioGroup;
    private RadioGroup secondRadioGroup;
    private int oldId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Position position = new Position(getIntent().getDoubleExtra(LONGITUDE,0),getIntent().getDoubleExtra(LATITUDE,0));
        Profile profile = getIntent().getParcelableExtra(PROFILE);


        // Sélection type incident
        firstRadioGroup = findViewById(R.id.radioGroupIncident1);
        secondRadioGroup = findViewById(R.id.radioGroupIncident2);

        firstRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    secondRadioGroup.clearCheck();
                    RadioButton oldCheckedButton = findViewById(oldId);
                    if (oldCheckedButton != null)
                        (findViewById(oldId)).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    RadioButton checkedRadioButton = findViewById(checkedId);
                    Toast toast = Toast.makeText(getApplicationContext(), "Type d'incident choisi : " + checkedRadioButton.getTag(), Toast.LENGTH_SHORT);
                    toast.show();
                    oldId = checkedId;
                    checkedRadioButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03A82F")));
                }
            }
        });
        secondRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    firstRadioGroup.clearCheck();
                    RadioButton oldCheckedButton = findViewById(oldId);
                    if (oldCheckedButton != null)
                        (findViewById(oldId)).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    RadioButton checkedRadioButton = findViewById(checkedId);
                    Toast toast = Toast.makeText(getApplicationContext(), "Type d'incident choisi : " + checkedRadioButton.getTag(), Toast.LENGTH_SHORT);
                    toast.show();
                    oldId = checkedId;
                    checkedRadioButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03A82F")));
                }
            }
        });


        // Sélection communauté
        Spinner spinnerCommunity = findViewById(R.id.spinnerCommunity);
        spinnerCommunity.setAdapter(new ArrayAdapter<Community>(this, android.R.layout.simple_spinner_item, Community.values()));


        // Bouton annuler
        (findViewById(R.id.buttonCancel)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            intentSend.putExtra(PROFILE, (Parcelable) profile);
            startActivity(intentSend);
        });


        // Bouton valider
        (findViewById(R.id.buttonSubmit)).setOnClickListener( click -> {

            // Récupérer incident choisi
            int chkId1 = firstRadioGroup.getCheckedRadioButtonId();
            int chkId2 = secondRadioGroup.getCheckedRadioButtonId();
            int idIncidentChecked = chkId1 == -1 ? chkId2 : chkId1;
            String strIncident = "";
            if (idIncidentChecked != -1)
                strIncident = (findViewById(idIncidentChecked)).getTag().toString();

            TypeIncident typeIncidentChecked;
            switch(strIncident) {
                case "Accident":
                    typeIncidentChecked = TypeIncident.ACCIDENT;
                    break;
                case "Danger":
                    typeIncidentChecked = TypeIncident.DANGER;
                    break;
                case "Route fermée":
                    typeIncidentChecked = TypeIncident.ROAD_CLOSED;
                    break;
                case "Police":
                    typeIncidentChecked = TypeIncident.POLICE;
                    break;
                case "Trafic ralenti":
                    typeIncidentChecked = TypeIncident.TRAFFIC_JAM;
                    break;
                case "Nid de poule":
                    typeIncidentChecked = TypeIncident.POTHOLE;
                    break;
                case "Travaux":
                    typeIncidentChecked = TypeIncident.WORKSITE;
                    break;
                default:
                    typeIncidentChecked = null;
                    break;
            }

            // Récupérer communauté choisie
            Community communitySelected = (Community) spinnerCommunity.getSelectedItem();

            // Récupérer description
            String description = ((EditText) findViewById(R.id.description)).getText().toString();
            if (description.equals(""))
                description = "Pas de description.";

            if (typeIncidentChecked != null) {

                // Contruire l'objet Incident

                Factory factory = new FactorySimple();
                try {
                    Incident incident = factory.buildIncident(typeIncidentChecked, communitySelected, description, position);

                    // Envoyer l'objet Incident à MainActivity
                    Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
                    intentSend.putExtra(INCIDENT, incident);
                    intentSend.putExtra(PROFILE, (Parcelable) profile);
                    startActivity(intentSend);

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            } else {

                // Message d'erreur lorsque pas de type d'incident sélectionné
                AlertDialog alertDialog = new AlertDialog.Builder(ReportActivity.this).create();
                alertDialog.setTitle("Champ non rempli !");
                alertDialog.setMessage("Vous devez obligatoirement sélectionner un type d'incident.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        });

    }
}