package projet.ihm.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import projet.ihm.R;

import static android.content.Context.LOCATION_SERVICE;

public class GPSFragment extends Fragment {
    private static final String TAG = "FRED_GPS_Fragment";
    private MainActivity gpsActivity;
    private Location currentLocation;
    private TextView textViewPlaceName;

    GPSFragment() { }

    GPSFragment(MainActivity activity) { gpsActivity = activity; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gps, container, false);
        textViewPlaceName = rootView.findViewById(R.id.placeName);
        final ImageView imageGPSGranted = rootView.findViewById( R.id.imageGPSGranted );
        final ImageView imageGPSActivated = rootView.findViewById(R.id.imageGPSActivated);

        //check if GPS permission is already GRANTED
        boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Log.d(TAG, "permissionGranted = " + permissionGranted);
        if (permissionGranted) {
            imageGPSGranted.setImageResource(R.drawable.gpson);
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLocation = location;
                    gpsActivity.moveCamera();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Log.d(TAG, "satus changed=" + s);
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.d(TAG, s+" sensor ON");
                    imageGPSActivated.setImageResource(R.drawable.unlocked);
                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.d(TAG, s+" sensor OFF");
                    imageGPSActivated.setImageResource(R.drawable.locked);
                }
            };
            LocationManager locationManager = (LocationManager) (getActivity().getSystemService(LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, listener);
            imageGPSActivated.setImageResource( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ? R.drawable.unlocked : R.drawable.locked );

        }else {
            //GPS permission is still not GRANTED
            imageGPSGranted.setImageResource(R.drawable.gpsoff);
            imageGPSActivated.setImageResource(R.drawable.locked);
            Log.d(TAG, "Permission NOT GRANTED  ! ");
        }
        return rootView;
    }


    GeoPoint getPosition() {
        return new GeoPoint(  currentLocation.getLatitude(), currentLocation.getLongitude() );
    }


    String getPlaceName() throws IOException {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        String cityName = addresses.get(0).getLocality();
        return cityName;
    }

    void setPlaceName(String placeName ) {
        textViewPlaceName.setText( placeName );
    }
}
