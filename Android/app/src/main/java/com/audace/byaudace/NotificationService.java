package com.audace.byaudace;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NotificationService extends IntentService {

    private Handler handler = null;
    private Thread thread;
    private int id_count;
    private RequestQueue queue;
    private SharedPreferences pref;

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
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        handler = new Handler(){

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("notifs",false)){
                    // Commande à exécuter à chaque intervalle de temps

                    int cc = id_count;
                    getNombrePhrases();
                    System.out.println("COUCOUCOUCOUCOU id_count=" + id_count + " cc=" + cc);

                    if(id_count > cc){
                        // déclencher la notification
                        Toast.makeText(getApplicationContext(), "Nouvelles phrases postées !", Toast.LENGTH_SHORT).show();
                        sendNotifications(id_count - cc);
                    }else{
                        Toast.makeText(getApplicationContext(), "Pas de nouvelles phrases :(", Toast.LENGTH_SHORT).show();
                    }

                }
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


    public void getNombrePhrases(){

        if(pref != null) {

            queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.SERVER + "/v1/phrase",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String json) {
                            try {
                                byte[] u = json.getBytes("ISO-8859-1");
                                json = new String(u, "UTF-8");
                                JSONArray mArray = null;
                                try {
                                    mArray = new JSONArray(json);
                                } catch (Exception e) {}

                                id_count = 0;
                                int proto = 0;

                                for (int i = 0; i < json.length(); i++) {
                                    try {
                                        proto = mArray.getJSONObject(i).getInt("id");
                                        id_count = id_count + 1;
                                    } catch (JSONException e) {
                                    }
                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //System.err.println(error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "basic " + Base64.encodeToString((pref.getString("login", null) + ":" + pref.getString("mdp", null)).getBytes(), Base64.NO_WRAP));
                    return params;
                }

            };

            queue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Pour pouvoir utiliser le service de notifications ByAudace, vous devez enregistrer vos informations de connexion.", Toast.LENGTH_LONG).show();
        }
    }


    // public void sendNotifications(View view){
    public void sendNotifications(int newphrases){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("ByAudace")
                        .setContentText(newphrases + " nouveaux besoins ont été postés !");
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

}
