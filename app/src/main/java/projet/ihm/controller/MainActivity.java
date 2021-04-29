package projet.ihm.controller;



import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.incident.Accident;
import projet.ihm.model.incident.Incident;

import static projet.ihm.model.incident.Incident.INCIDENT;

public class MainActivity extends FragmentActivity implements IGPSActivity, OnMapReadyCallback {
    private static final String TAG = "GPS";
    private Location currentLocation;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Demander la permission pour utiliser le GPS
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        // Met à jour la position
        updatePosition();



        // bouton profile
        (findViewById(R.id.buttonProfile)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ProfileActivity.class);
            startActivity(intentSend);
        });

        // bouton signaler
        (findViewById(R.id.buttonReport)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ReportActivity.class);
            startActivity(intentSend);
        });

        // bouton appeler
        (findViewById(R.id.buttonCall)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), CallActivity.class);
            startActivity(intentSend);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePosition();


        // Récupérer un incident et le placer sur la map à la localisation actuelle
        Incident incidentReceived = getIntent().getParcelableExtra(INCIDENT);

        if (incidentReceived != null) {

        }

    }

    public void updatePosition() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLocation = location;
                    // Marker de la localisation actuelle
                    mMap.addMarker(new MarkerOptions().position(getPosition()).title("Votre localisation"));
                    moveCamera();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Log.d(TAG, "satus changed=" + s);
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.d(TAG, s + " sensor ON");
                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.d(TAG, s + " sensor OFF");
                }
            };
            LocationManager locationManager = (LocationManager) (this.getSystemService(LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, listener);

        } else {
            //GPS permission is still not GRANTED
            Log.d(TAG, "Permission NOT GRANTED  ! ");
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void moveCamera() {
        // Zoom animé
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getPosition(),15));
    }

    // Obtenir la position
    LatLng getPosition() {
        return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }


    // Pour les permissions de localisation
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d( TAG, "requestCode=" + requestCode );

        switch (requestCode) {
            case REQUEST_CODE: {  //GPS FINE LOCATION only autorisation result code
                if( grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Permission de localisation activé.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    // Message d'erreur lorsque pas de type d'incident sélectionné
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Attention !");
                    alertDialog.setMessage("Pour que l'application fonctionne vous devez permettre à l'application d'utiliser la localisation. " +
                            "L'application va se fermer.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                    System.exit(0);
                                }
                            });
                    alertDialog.show();
                }
            } break;
        }
    }
}