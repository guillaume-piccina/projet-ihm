package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import projet.ihm.R;

public class MainActivity extends AppCompatActivity {
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IMapController mapController;
        super.onCreate(savedInstanceState);

        // pour la map
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_main);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);               // zoomable
        map.setMultiTouchControls(true);                //  zoom with 2 fingers

        mapController = map.getController();
        mapController.setZoom(18.0);
        GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
        mapController.setCenter(startPoint);





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

    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        map.onPause();
    }
}