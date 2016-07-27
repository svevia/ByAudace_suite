package com.audace.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

/**
 * MainActivity est l'activité de login, où l'utilisateur doit entrer login et mot de passe afin de s'authentifier.
 * Si les identifiants entrés sont corrects, l'utilisateur est authentifié au serveur, et redirigé vers l'activité
 * ChoiceActivity.
 * Une fonction "remember me" est présente, afin que l'utilisateur puisse s'il le désire garder en mémoire ses
 * identifiants afin de ne pas devoir les retaper à chaque utilisation de l'application.
 */
public class MainActivity extends Activity {

    private TextView mdpoublie;
    private EditText loginText;
    private EditText passwordText;
    private RequestQueue queue;
    private boolean ok = false;
    private SharedPreferences pref;
    private CheckBox checkbox;
    private User user;

    /**
     * La méthode onCreate surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsqu'une activité JpeuxAiderActivity est créée avec un Intent,
     * ou lorsque le terminal change d'orientation ; le bundle passé en paramètre permet alors
     * la sauvegarde des données.
     * Celle-ci initialise également les attributs privés de cette activité.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);


        // animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        loginText = (EditText) findViewById(R.id.LoginText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        mdpoublie = (TextView) findViewById(R.id.mdpOublie);

        mdpoublie.setText(Html.fromHtml("<a href=''>Mot de passe oublié ?</a>"));
        mdpoublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdpOublie();
            }
        });

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


    /**
     * La méthode login, appelée par défaut lorsque l'utilisateur presse le bouton "Connexion", communique avec le
     * serveur via une requête GET et vérifie si les identifiants entrés sont valides.
     */
    public void login(final View view){
        final String login = ""+loginText.getText();
        final String password = "" + passwordText.getText();

        queue = Volley.newRequestQueue(this);

        /*
        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/mail="+login.toLowerCase(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        System.out.println("Json : " + json);
                        String[] tok = json.split(",");

                        // A corriger : Les champs peuvent être nuls dans le User
                        user = new User(Integer.valueOf(tok[0].split(":")[1]), tok[1].split(":")[1], tok[2].split(":")[1], tok[3].split(":")[1]);
                        System.out.println("User : " + user.toString());
                        load(login, password);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : " + error.getMessage());
            }

        });
        queue.add(request);
        */

        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/salt?mail="+login.toLowerCase(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        System.out.println("Json : " + json);
                        //String[] tok = json.split(",");

                        // A corriger : Les champs peuvent être nuls dans le User
                        //user = new User(Integer.valueOf(tok[0].split(":")[1]), tok[1].split(":")[1], tok[2].split(":")[1], tok[3].split(":")[1]);
                        //System.out.println("User : " + user.toString());
                        load(json, login, password);
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

    /**
     * La méthode load, appelée par défaut par la méthode login, authentifie l'utilisateur et le redirige sur
     * l'activité ChoiceActivity, ou, le cas échéant, informe l'utilisateur via une pop-up qu'un problème est
     * survenu durant l'authentification.
     * Si c'est le cas, la nature du problème est indiquée clairement à l'utilisateur.
     */
    private void load(final String salt, final String login, final String mdp){

        String URL = Configuration.SERVER + "/v1/auth/";

        if (login.replace(" ", "").replace("?", "").equals("")){
            alertNotification(android.R.drawable.ic_delete,"Champs vides","Entrez votre adresse mail et votre mot de passe.");
            return;
        }

        queue = Volley.newRequestQueue(this);
        final String hashSalt = buildHash(mdp, salt);
        URL += login.toLowerCase();

        System.out.println(hashSalt);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"?mot_de_passe="+hashSalt,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                        activity.putExtra("user_mail", login);
                        activity.putExtra("mdp", hashSalt);
                        startActivity(activity);
                        //finish();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // gestion approfondie des erreurs

                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    alertNotification(android.R.drawable.ic_delete, "Erreur réseau","Vérifiez votre connection Internet.");

                } else if (error instanceof AuthFailureError) {
                    alertNotification(android.R.drawable.ic_delete,"Erreur","Identifiant mail ou mot de passe incorrect.");

                } else if (error instanceof ServerError) {
                    alertNotification(android.R.drawable.ic_popup_sync,"Maintenance en cours","Le serveur est actuellement indisponible, veuillez réessayer plus tard.");

                } else {
                    alertNotification(android.R.drawable.ic_delete,"ParseError",error.getMessage());
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


    public void checkButtonClicked(View view){

        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();

        if(checkbox.isChecked()) {

            pref.edit().putString("login", login)
                    .putString("mdp", password)
                    .apply();

        }else{
            pref.edit().putString("login", null)
                    .putString("mdp", null)
                    .apply();
        }
    }

    /**
     * La méthode onBackPressed surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsque l'utilisateur presse le bouton "retour" de
     * son terminal.
     */
    @Override
    public void onBackPressed() {

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

    }

    /**
     * La méthode onStop permet de vider la file inhérente à Volley.
     */
    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(this);
        }
    }

    /**
     * La méthode alertNotification permet d'afficher une fenêtre de type "pop-up" au dessus de l'activité
     * en cours. Cette fenêtre possède un bouton de validation, qui appelle alertContact(), et un bouton de
     * retrait, qui ferme simplement la pop-up.
     *
     * @param icon Icône à afficher à gauche du titre de la fenêtre (Android.R.drawable.ic*)
     * @param title Texte à afficher en titre de la fenêtre
     * @param text Texte à afficher en tant que texte de corps de la fenêtre
     */
    public void alertNotification(int icon, String title, String text){
        AlertDialog.Builder alertDialogBuilder;
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

    /**
     * La méthode buildHash permet de générer un hash à partir d'un mot de passe et d'une chaîne de caractères.
     */
    public String buildHash(String mot_de_passe, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(mot_de_passe + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }


    public void mdpOublie(){
        queue = Volley.newRequestQueue(this);

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Mot de passe oublié ?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Entrez votre adresse mail");
        //input.setBackgroundResource(R.drawable.edit_text);
        alertDialogBuilder.setView(input);

        // set dialog message
        alertDialogBuilder
                .setMessage("Un mail contenant votre nouveau mot de passe vous sera envoyé.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // création utilisateur
                        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/lost/"+input.getText().toString(),
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String json) {}

                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("ERROR : " + error.getMessage());
                            }

                        });
                        queue.add(request);

                        Toast.makeText(getApplicationContext(), "Un mail contenant votre nouveau mot de passe vous a été envoyé.", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

}
