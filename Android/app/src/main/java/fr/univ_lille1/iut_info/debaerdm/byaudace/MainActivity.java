package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

    private final String URL = Configuration.SERVER + "/v1/userdb";
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
    }

    private void load(String login, String mdp, final View view){
        if (login.replace(" ", "").replace("?", "").equals("")){
            alertNotification(view,"Champs vides !","Entrez votre mail et votre mot de passe.");
            return;
        }
        queue = Volley.newRequestQueue(this);

        final StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, URL + "/" +login.toLowerCase(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        System.out.println("Login success");
                        Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                        startActivity(activity);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.err.println(error.getMessage());
                alertNotification(view,"Erreur !","Mauvais identifiant ou mauvais mot de passe.");
            }
        });

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

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(this);
        }
    }

    public void onChangeActivity(View view){

        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();

        load(login, password, view);
    }

    public void alertNotification(View view, String title, String text){
    if (!ok) {
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
                        // if this button is clicked, close
                        // current activity
                        // MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        ok = !ok;
    }else{
        ok = !ok;
    }


    }
}
