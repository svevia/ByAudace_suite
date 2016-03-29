package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String LOGIN[] = {"","Toto", "Tutu", "Tata"};
    private static final String MDP[] = {"","toto", "tutu", "tata"};
    private Button loginButton;
    private EditText loginText;
    private EditText passwordText;
    private PopupWindow errorMessage;
    private LinearLayout layout;
    private TextView tv;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private boolean ok = false;
    private SharedPreferences pref;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.LogButton);
        loginText = (EditText) findViewById(R.id.LoginText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        errorMessage = new PopupWindow(this);
        layout = new LinearLayout(this);
        tv = new TextView(this);
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


    public void login(View view){

        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();

        load(login, password, view);

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
            alertNotification(view,"Champs vides !","Entrez votre mail et votre mot de passe.");
            return;
        }

        queue = Volley.newRequestQueue(this);

        URL += login.toLowerCase();

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"?mot_de_passe="+mdp,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                        startActivity(activity);
                        finish();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //gestion approfondie des erreurs
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                } else if (error instanceof AuthFailureError) {
                    alertNotification(view,"Erreur !","Mauvais identifiant ou mauvais mot de passe.");

                } else if (error instanceof ServerError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    //TODO

                } else if (error instanceof ParseError) {
                    //TODO

                }
            }

        }) /*{

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mdp", mdp);
                return params;
            }
        }*/;

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
    @Override
    public void onBackPressed(){
        this.finish();

    }


    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(this);
        }
    }


    public void alertNotification(View view, String title, String text){
        if (!ok) {
            ok = true;
            alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(title);

            // set dialog message
            alertDialogBuilder
                    .setMessage(text)
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
}
