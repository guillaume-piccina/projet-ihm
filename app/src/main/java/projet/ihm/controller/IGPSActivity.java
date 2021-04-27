package projet.ihm.controller;

public interface IGPSActivity {
    int REQUEST_CODE = 400;  // request code used in requestPermissions() méthode and its callback onRequestPermissionsResult()
    void moveCamera();      // move camera (with zoom) to center the map to the GPS position
}
