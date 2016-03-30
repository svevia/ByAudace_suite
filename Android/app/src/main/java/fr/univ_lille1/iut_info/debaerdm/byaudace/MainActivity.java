package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private EditText loginText;
    private EditText passwordText;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private boolean ok = false;
    private SharedPreferences pref;
    private CheckBox checkbox;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        loginText = (EditText) findViewById(R.id.LoginText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        // si l'utilisateur a mis en mémoire ses identifiants
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(pref != null) {

            loginText.setText(pref.getString("login", null));
            passwordText.setText(pref.getString("mdp", null));

            // si des identifiants sont en mémoire, on coche la checkbox
            if(pref.contains("login") && pref.contains("mdp")) {
                checkbox.setChecked(true);

            }

        }
    }


    public void login(final View view){
        final String login = ""+loginText.getText();
        final String password = "" + passwordText.getText();

        queue = Volley.newRequestQueue(this);

        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/user?mail="+login.toLowerCase(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        System.out.println("Json : " + json);
                        String[] tok = json.split(",");
                        user = new User(tok[0].split(":")[1], tok[1].split(":")[1], tok[2].split(":")[1], tok[3].split(":")[1]);
                        System.out.println("User : " + user.toString());
                        load(login, password, view);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : " + error.getMessage());
            }

        });
        queue.add(request);

        // stockage des identifiants
        checkButtonClicked(this.getCurrentFocus());
    }

    public void checkButtonClicked(View view){

        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();

        if(checkbox.isChecked()) {

            pref.edit().putString("login", login)
                    .putString("mdp", password)
                    .commit();

        }else{
            pref.edit().putString("login", null)
                    .putString("mdp", null)
                    .commit();
        }
    }

    private void load(final String login, final String mdp, final View view){

        String URL = Configuration.SERVER + "/v1/auth/";

        if (login.replace(" ", "").replace("?", "").equals("")){
            alertNotification(view,android.R.drawable.ic_delete,"Champs vides","Entrez votre adresse mail et votre mot de passe.");
            return;
        }

        queue = Volley.newRequestQueue(this);
        final String hashSalt = buildHash(mdp, user.getSalt());
        URL += login.toLowerCase();

        System.out.println(hashSalt);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"?mot_de_passe="+hashSalt,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                        activity.putExtra("user_nom", user.getNom());
                        activity.putExtra("user_prenom", user.getPrenom());
                        activity.putExtra("user_numero", user.getNumero());
                        activity.putExtra("user_mail", login);
                        activity.putExtra("user_mot_de_passe", mdp);
                        startActivity(activity);
                        finish();
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // gestion approfondie des erreurs

                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    alertNotification(view, android.R.drawable.ic_delete, "Erreur réseau","Vérifiez votre connection Internet.");

                } else if (error instanceof AuthFailureError) {
                    alertNotification(view, android.R.drawable.ic_delete,"Erreur","Identifiant mail ou mot de passe incorrect.");

                } else if (error instanceof ServerError) {
                    alertNotification(view, android.R.drawable.ic_popup_sync,"Maintenance en cours","Le serveur est actuellement indisponible, veuillez réessayer plus tard.");

                } else {
                    System.out.println("ERROR : " + error.getMessage());
                    //alertNotification(view, android.R.drawable.ic_delete,"ParseError",error.getMessage());

                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                System.out.println(params.toString());
                return params;
            }
        };


        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // retour = fermeture de l'application

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Quitter !")
                    .setMessage("Voulez-vous vraiment quitter ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            finish();
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }


    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(this);
        }
    }


    public void alertNotification(View view, int icon, String title, String text){
        if (!ok) {
            ok = true;
            alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(title);

            // set dialog message
            alertDialogBuilder
                    .setMessage(text)
                    .setIcon(icon)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ok = false;
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    public String buildHash(String mot_de_passe, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(mot_de_passe + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }

}
