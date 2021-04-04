package projet.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        (findViewById(R.id.buttonProfile)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ProfileActivity.class);
            startActivity(intentSend);
        });

        (findViewById(R.id.buttonReport)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ReportActivity.class);
            startActivity(intentSend);
        });

        (findViewById(R.id.buttonCall)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), CallActivity.class);
            startActivity(intentSend);
        });

    }
}