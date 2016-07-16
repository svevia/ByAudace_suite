package com.audace.byaudace;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class NotificationService extends IntentService {

    private Handler handler = null;
    private Thread thread;


    public NotificationService(){
        super("NotificationService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(getApplicationContext(), "Service handled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler(){

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("notifs",false))
                    Toast.makeText(getApplicationContext(), "Hey !", Toast.LENGTH_SHORT).show();
            }

        };

        thread = new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                while(true)
                {
                    try {
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(0);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        });
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // The application must be turned off in order to kill the thread
        super.onDestroy();
    }

}
