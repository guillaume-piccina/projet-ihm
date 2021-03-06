package projet.ihm.controller;



import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import projet.ihm.R;
import projet.ihm.model.Community;
import projet.ihm.model.Position;
import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.TypeIncident;

import static projet.ihm.controller.ParametersActivity.COMMUNITY;
import static projet.ihm.controller.ParametersActivity.DISTANCE;
import static projet.ihm.model.Application.LATITUDE;
import static projet.ihm.model.Application.LONGITUDE;
import static projet.ihm.model.incident.Incident.INCIDENT;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "GPS";
    private Location currentLocation;
    private GoogleMap mMap;
    private View fragmentInfoIncident;
    private Marker marker;
    private Incident incident;
    private Incident demoIncident= new Incident(TypeIncident.ACCIDENT, Community.EVERYBODY, "", new Position(5.3744, 43.2949));


    // Notification
    private int notificationId = 0;
    public static final String CHANNEL_ID = "channel";
    private static NotificationManager notificationManager;

    // APPEL 18
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    private static final String LOG_TAG = "AndroidExample";

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume(){
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    currentLocation = new Location(""); //provider name is unnecessary
                    currentLocation.setLatitude((double) intent.getExtras().get("latitude"));
                    currentLocation.setLongitude((double) intent.getExtras().get("longitude"));
                    placeMarker(getPosition());
                    showIncident();
                    sendDemoNotification();
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        }
    }

    private void placeMarker(LatLng location) {
        if ( marker != null ) {
            marker.setPosition(location);
        } else {
            marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(getPosition())
                            .draggable(true)
                            .title("Vous"));


            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_accident))
                    .position(new LatLng(43.2949, 5.3744))
                    .title(TypeIncident.ACCIDENT.toString())
                    .snippet("Description : Pas de description\n\n  Date : 05/05/2021 14:10:52"));
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), MapService.class);
        startService(intent);

        // Notification
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel pour l'application incident de la route.");
        notificationManager = getSystemService(NotificationManager.class);
        Objects.requireNonNull(notificationManager).createNotificationChannel(channel);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Demander la permission pour utiliser le GPS
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // bouton profile
        (findViewById(R.id.buttonProfile)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ParametersActivity.class);
            startActivity(intentSend);
        });

        // bouton signaler
        (findViewById(R.id.buttonReport)).setOnClickListener( click -> {
            Intent intentSend = new Intent( getApplicationContext(), ReportActivity.class);
            intentSend.putExtra(LONGITUDE, currentLocation.getLongitude());
            intentSend.putExtra(LATITUDE, currentLocation.getLatitude());
            startActivity(intentSend);
        });

        (findViewById(R.id.buttonCall)).setOnClickListener( click ->
            askPermissionAndCall()
        );

        //Bouton Recentrer
        (findViewById(R.id.Recentrer)).setOnClickListener( click ->
                moveCamera()
        );




    }

    public void showIncident() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Incident incidentReceived = getIntent().getParcelableExtra(INCIDENT);

        if (incidentReceived != null) {
            incident = incidentReceived;
            String community = preferences.getString(COMMUNITY, "Tout le monde");
            if (community.equals(incidentReceived.getCommunity().toString()) || community.equals(Community.EVERYBODY.toString()) || incidentReceived.getCommunity().toString().equals(Community.EVERYBODY.toString())) {
                int icon;
                switch (incidentReceived.getType()) {
                    case "Accident":
                        icon = R.drawable.ic_accident;
                        break;
                    case "Danger":
                        icon = R.drawable.ic_danger;
                        break;
                    case "Route ferm??e":
                        icon = R.drawable.ic_road_closed;
                        break;
                    case "Trafic ralenti":
                        icon = R.drawable.ic_traffic_jam;
                        break;
                    case "Travaux":
                        icon = R.drawable.ic_worksite;
                        break;
                    case "Police":
                        icon = R.drawable.ic_police;
                        break;
                    case "Nid de poule":
                        icon = R.drawable.ic_pothole;
                        break;
                    default:
                        icon = R.drawable.ic_others;
                        break;
                }
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapFromVector(getApplicationContext(), icon))
                        .position(incidentReceived.getPositiontoLatLng())
                        .title(incidentReceived.getType())
                        .snippet("Description : " + incidentReceived.getDescription() + "\n\n" + incidentReceived.getDate()));
                sendIncidentNotification();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Customiser les infos du marker
        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }
                @Override
                public View getInfoContents(Marker marker) {
                    if (!marker.getTitle().equals("Votre localisation")) {

                        View infoWindowLayout  = getLayoutInflater().inflate(R.layout.fragment_info_incident, null);
                        fragmentInfoIncident = infoWindowLayout.findViewById(R.id.infoIncident);
                        ((TextView) fragmentInfoIncident.findViewById(R.id.title)).setText(marker.getTitle());
                        ((TextView) fragmentInfoIncident.findViewById(R.id.description)).setText(marker.getSnippet());
                        return fragmentInfoIncident;
                    } else {
                        return null;
                    }
                }
            });

        }
    }

    public void moveCamera() {
        // Zoom anim??
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
            case 1: {  //GPS FINE LOCATION only autorisation result code
                if( grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Permission de localisation activ??.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    // Message d'erreur lorsque pas de type d'incident s??lectionn??
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Attention !");
                    alertDialog.setMessage("Pour que l'application fonctionne vous devez permettre ?? l'application d'utiliser la localisation. " +
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

            case MY_PERMISSION_REQUEST_CODE_CALL_PHONE: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    this.callNow();
                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    // Pour les icons sur la map des incidents
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, 100, 100);

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void askPermissionAndCall() {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        // 23

        // Check if we have Call permission
        int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
            // If don't have permission so prompt the user.
            this.requestPermissions(
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSION_REQUEST_CODE_CALL_PHONE
            );
            return;
        }
        this.callNow();
    }

    private void callNow() {
        String phoneNumber = "18";

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            this.startActivity(callIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Your call failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_CALL_PHONE) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Envoie une notification quand un incident est dans la zone
    public void sendIncidentNotification() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int distanceNotif = Integer.parseInt(preferences.getString(DISTANCE, "50"));

        Location incidentLocation = new Location("");
        incidentLocation.setLatitude((double) incident.getPosition().getLatitude());
        incidentLocation.setLongitude((double) incident.getPosition().getLongitude());
        float distance = currentLocation.distanceTo(incidentLocation);

        if (distance <= distanceNotif && !incident.isSend()) {
            sendNotification("Incident de type " + incident.getType() + " ?? moins de " + distanceNotif + " m de votre position");
            incident.send();
        }

    }

    public void sendDemoNotification(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int distanceNotif = Integer.parseInt(preferences.getString(DISTANCE, "50"));

        Location demoIncidentLocation = new Location("");
        demoIncidentLocation.setLatitude((double) demoIncident.getPosition().getLatitude());
        demoIncidentLocation.setLongitude((double) demoIncident.getPosition().getLongitude());
        float demoDistance = currentLocation.distanceTo(demoIncidentLocation);

        if (demoDistance <= distanceNotif && !demoIncident.isSend()) {
            sendNotification("Incident de type " + demoIncident.getType() + " ?? moins de " + distanceNotif + " m de votre position");
            demoIncident.send();
        }
    }

    private void sendNotification(String message) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Incident proche de vous !")
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManagerCompat.from(this).notify(++notificationId, notification.build());
    }

}