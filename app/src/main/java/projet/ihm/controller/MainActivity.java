package projet.ihm.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;

import projet.ihm.R;
import projet.ihm.model.Incident;
import projet.ihm.model.TypeIncident;

import static projet.ihm.controller.IGPSActivity.REQUEST_CODE;

public class MainActivity extends AppCompatActivity {
    private MapView map;
    private static final String TAG = "FRED_MapsActivity";
    private GPSFragment gpsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IMapController mapController;
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        NavigationFragment navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation);
        if (navigationFragment == null) {
            navigationFragment = new NavigationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.navigation, navigationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        gpsFragment = (GPSFragment) getSupportFragmentManager().findFragmentById(R.id.gpsLocation);
        if (gpsFragment == null) {
            gpsFragment = new GPSFragment( this );
            FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
            gpsTransaction.replace( R.id.gpsLocation, gpsFragment );
            gpsTransaction.addToBackStack(null);
            gpsTransaction.commit();
        }

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


    public void moveCamera() {
        try {
            gpsFragment.setPlaceName( "Ville: " + gpsFragment.getPlaceName() );
        } catch (IOException e) {
            gpsFragment.setPlaceName( getString(R.string.placeName) );
        }
        IMapController mapController = map.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(gpsFragment.getPosition());
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE: {  //GPS FINE LOCATION only autorisation result code
                if( grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE authorisation Granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            } break;
        }
        //refresh display
        gpsFragment = new GPSFragment( this );
        FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
        gpsTransaction.replace( R.id.gpsLocation, gpsFragment );
        gpsTransaction.addToBackStack(null);
        gpsTransaction.commit();
    }

    public void onMapReady(MapView mapView) {
        map = mapView;
    }

}