package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import projet.ihm.R;

public class ReportActivity extends AppCompatActivity {
    private RadioGroup firstRadioGroup;
    private RadioGroup secondRadioGroup;
    int oldId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        (findViewById(R.id.buttonCancel)).setOnClickListener( click -> {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentSend);
        });


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


        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }
}