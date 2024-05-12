package com.example.docappli;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class Notification {
    private static final String CHANNEL_ID = "doctorappointment_notification_channel";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager NotiManager;
    private Context myContext;

    public Notification(Context context) {
        this.myContext = context;
        this.NotiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Service notification",
                NotificationManager.IMPORTANCE_HIGH);

        channel.enableLights(true);
        channel.setLightColor(Color.MAGENTA);
        channel.enableVibration(true);
        channel.setDescription("Notifications from Service application.");
        NotiManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        Intent intent = new Intent(myContext, MakeAppointmentActivity.class);
        PendingIntent pendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(myContext,
                    NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        } else {
            pendingIntent = PendingIntent.getActivity(myContext,
                    NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Appointment")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotiManager.notify(NOTIFICATION_ID, builder.build());
    }
}
