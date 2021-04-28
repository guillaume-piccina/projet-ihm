package projet.ihm.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.Factory;
import projet.ihm.model.FactorySimple;
import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.TypeIncident;

public class ReportActivity extends AppCompatActivity {
    private RadioGroup firstRadioGroup;
    private RadioGroup secondRadioGroup;
    private int oldId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


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
            startActivity(intentSend);
        });



        // Bouton valider
        (findViewById(R.id.buttonSubmit)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);

            // Récupérer incident choisi
            int chkId1 = firstRadioGroup.getCheckedRadioButtonId();
            int chkId2 = secondRadioGroup.getCheckedRadioButtonId();
            int idIncidentChecked = chkId1 == -1 ? chkId2 : chkId1;
            String strIncident = "";
            if (idIncidentChecked != -1)
                strIncident = (findViewById(idIncidentChecked)).getTag().toString();

            TypeIncident typeIncidentChecked;
            switch(strIncident) {
                case "accident":
                    typeIncidentChecked = TypeIncident.ACCIDENT;
                    break;
                case "danger":
                    typeIncidentChecked = TypeIncident.DANGER;
                    break;
                case "roadClosed":
                    typeIncidentChecked = TypeIncident.ROAD_CLOSED;
                    break;
                case "police":
                    typeIncidentChecked = TypeIncident.POLICE;
                    break;
                case "trafficJam":
                    typeIncidentChecked = TypeIncident.TRAFFIC_JAM;
                    break;
                case "pothole":
                    typeIncidentChecked = TypeIncident.POTHOLE;
                    break;
                case "worksite":
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
            System.out.println(description);

            if (typeIncidentChecked != null) {

                // Contruire l'objet Incident
                Factory factory = new FactorySimple();
                try {
                    Incident incident = factory.buildIncident(typeIncidentChecked, communitySelected, description);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                // Envoyer l'objet Incident à MainActivity

                startActivity(intentSend);

            } else {

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