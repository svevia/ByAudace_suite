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
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    public Switch notifs;
    private SharedPreferences pref;
    private Intent notificationIntent;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);
        intent = this.getIntent();
        notificationIntent = new Intent(this,NotificationService.class);
        notifs = (Switch)findViewById(R.id.switchNotifications);
        notifs.setClickable(false);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TextView bonjour = (TextView)findViewById(R.id.textView2);
        bonjour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cette option est actuellement en cours de développement.", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void start(){
        //startService(new Intent(getBaseContext(),NotificationService.class));
        this.startService(notificationIntent);
    }

    public void stop(){
        //stopService(new Intent(getBaseContext(),NotificationService.class));
        stopService(notificationIntent);
    }

    public void changeInfos(View view){
        Intent coucou = new Intent(SettingsActivity.this, InfosActivity.class);
        coucou.putExtra("id", intent.getIntExtra("id",0));
        coucou.putExtra("salt",intent.getStringExtra("salt"));
        coucou.putExtra("user_digit",intent.getStringExtra("user_digit"));
        coucou.putExtra("user_nom", intent.getStringExtra("user_nom"));
        coucou.putExtra("user_prenom", intent.getStringExtra("user_prenom"));
        coucou.putExtra("user_numero", intent.getStringExtra("user_numero"));
        coucou.putExtra("user_mail", intent.getStringExtra("user_mail"));
        coucou.putExtra("user_mot_de_passe", intent.getStringExtra("user_mot_de_passe"));
        startActivity(coucou);
        //this.finish();
    }
}
