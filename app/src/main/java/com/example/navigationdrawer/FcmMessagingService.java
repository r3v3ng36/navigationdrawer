package com.example.navigationdrawer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {

    String type = "";
    String id = "",message = "",title = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean NOTIFICTION = prefs.getBoolean("NOTIFICTION",false);
        if (remoteMessage.getData().size() > 0){
            type = "json";
            if (NOTIFICTION.equals(true)) {
            }else {
                sendNotification(remoteMessage.getData().toString());
            }
        }
        if (remoteMessage.getNotification() != null){
            type = "message";
            if (NOTIFICTION.equals(true)){
            } else {
                sendNotification(remoteMessage.getNotification().getBody());

            }

        }
    }

    private void sendNotification(String messageBody){


        if (type.equals("json")) {
            try {
                JSONObject jsonObject = new JSONObject(messageBody);
                id = jsonObject.getString("id");
                message = jsonObject.getString("message");
                title = jsonObject.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            else if (type.equals("message")){

                Intent intent = new Intent(this,NavigationDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setContentTitle(title);
                notificationBuilder.setContentText(message);
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder.setSound(soundUri);
                notificationBuilder.setSmallIcon(R.drawable.logo);
               // notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.splashscreen));
                notificationBuilder.setAutoCancel(true);
                Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(1000);
                notificationBuilder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0,notificationBuilder.build());

            }
        }

    }


