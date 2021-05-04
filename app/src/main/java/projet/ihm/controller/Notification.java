package projet.ihm.controller;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import projet.ihm.R;

public class Notification extends Application {
    private int notificationId = 0;
    public static final String CHANNEL_ID = "channel";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel pour l'application incident de la route.");
    }

    private void sendNotification(String message, int icon) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Incident proche de vous !")
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManagerCompat.from(this).notify(++notificationId, notification.build());
    }
}
