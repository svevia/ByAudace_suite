package com.audace.byaudace;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

public class SettingsActivity extends Activity {

    public Switch notifs;
    private SharedPreferences pref;
    private Intent notificationIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        notificationIntent = new Intent(this,NotificationService.class);
        notifs = (Switch)findViewById(R.id.switchNotifications);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(pref != null) {

            notifs.setChecked(false);

            if(pref.contains("notifs") && pref.getBoolean("notifs",false)) {
                notifs.setChecked(true);
                start();
            }

        }

    }

    public void setNotifications(View view){
        pref.edit().putBoolean("notifs", notifs.isChecked()).apply();
        if(pref.contains("notifs") && pref.getBoolean("notifs",false)) {
            start();
        }else{
            stop();
        }

    }

    public void sendNotifications(View view){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("ByAudace")
                        .setContentText("5 nouveaux besoins ont été postés !");
    // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, JpeuxAiderActivity.class);

    // The stack builder object will contain an artificial back stack for the
    // started Activity.
    // This ensures that navigating backward from the Activity leads out of
    // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(JpeuxAiderActivity.class);
    // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    // mId allows you to update the notification later on.
        mNotificationManager.notify(42, mBuilder.build());
    }

    public void start(){
        //startService(new Intent(getBaseContext(),NotificationService.class));
        this.startService(notificationIntent);
    }

    public void stop(){
        //stopService(new Intent(getBaseContext(),NotificationService.class));
        stopService(notificationIntent);
    }
}
