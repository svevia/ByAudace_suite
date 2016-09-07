package com.audace.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ChoiceActivity est l'activité de choix, où l'utilisateur est redirigé après s'être authentifié avec succès.
 * Celle-ci propose deux options :
 * "HELP", où l'utilisateur est redirigé vers l'activité HelpActivity afin de poster sa phrase ;
 * "J'PEUX AIDER", où l'utilisateur est redirigé vers l'activité JpeuxAiderActivity afin de consulter les phrases métier.
 */
public class ChoiceActivity extends Activity {

    private Intent intent;
    private User user;
    private String salt;

    /**
     * La méthode onCreate surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsqu'une activité ChoiceActivity est créée avec un Intent,
     * ou lorsque le terminal change d'orientation ; le bundle passé en paramètre permet alors
     * la sauvegarde des données.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        intent = this.getIntent();
        RequestQueue queue = Volley.newRequestQueue(this);

        // création utilisateur
        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/mail/"+intent.getStringExtra("user_mail"),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        try {
                            byte[] u = json.getBytes("ISO-8859-1");
                            json = new String(u, "UTF-8");
                        }catch(Exception e){}

                        System.out.println("BONSOUAR : " + json);
                        String[] tok = json.split(",");

                        // A corriger : Les champs peuvent être nuls dans le User
                        user = new User(tok[1].split(":")[1], //digit
                                Integer.valueOf(tok[2].split(":")[1]), //id
                                tok[3].split(":")[1], //mail
                                tok[4].split(":")[1], //mdp
                                tok[5].split(":")[1], //nom
                                tok[7].split(":")[1], //numero
                                tok[8].split(":")[1]); //prenom


                        System.out.println("USER : " + user.toString());
                        // Ici, on attrape l'utilisateur qui démarre l'appli pour la première fois
                        if(user.getNom().isEmpty() && user.getPrenom().isEmpty()){
                            //Toast.makeText(getApplicationContext(), "BONJOUR", Toast.LENGTH_SHORT).show();
                            intentClass(InfosActivity.class);
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : " + error.getMessage());
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "basic " + Base64.encodeToString((intent.getStringExtra("user_mail") + ":" + intent.getStringExtra("mdp")).getBytes(), Base64.NO_WRAP));
                return params;
            }
        };
        queue.add(request);
        setContentView(R.layout.activity_choice);
    }

    /**
     * La méthode onBackPressed surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsque l'utilisateur presse le bouton "retour" de
     * son terminal.
     * Ici, appeler cette méthode tue l'activité en fonctionnement et redirige l'utilisateur
     * sur la page de login.
     */
    @Override
    public void onBackPressed(){

        // redirection sur la page de login
        Intent i = new Intent( ChoiceActivity.this, MainActivity.class );
        startActivity(i);
        this.finish();
    }

    /**
     * La méthode helpme est par défaut appelée lorsque l'utilisateur appuie sur le bouton de demande d'aide
     * ("HELP"). Celle-ci redirige l'utilisateur vers l'activité HelpActivity, tout en lui passant grâce à
     * l'intent les données nécessaires au maintien de la connexion de sa session.
     */
    public void helpme(View v) {
        intentClass(HelpActivity.class);
    }

    /**
     * La méthode aider est par défaut appelée lorsque l'utilisateur appuie sur le bouton de consultation
     * des demandes d'aide ("J'PEUX AIDER"). Celle-ci redirige l'utilisateur vers l'activité JpeuxAiderActivity,
     * tout en lui passant grâce à l'intent les données nécessaires au maintien de la connexion de sa session.
     */
    public void aider(View v) {
        intentClass(JpeuxAiderActivity.class);
    }

    public void settings(View v) { intentClass(SettingsActivity.class); }

    public void intentClass(Class changeClass){
        intent.setClass(this, changeClass);
        intent.putExtra("salt",salt);
        intent.putExtra("id", user.getId());
        intent.putExtra("user_digit",user.getDigit());
        intent.putExtra("user_nom", user.getNom());
        intent.putExtra("user_prenom", user.getPrenom());
        intent.putExtra("user_numero", user.getNumero());
        intent.putExtra("user_mail", user.getMail());
        intent.putExtra("user_mot_de_passe", user.getMdp());

        if(changeClass == InfosActivity.class) {
            intent.putExtra("first_use", true);
        }

        startActivity(intent);
        //finish();
    }



    public void alertBeta(View view){
        final RequestQueue queue = Volley.newRequestQueue(this);

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Que pensez-vous de l'application ?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        input.setHint("Entrez votre message de retour ici");
        alertDialogBuilder.setView(input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        List<String> mails = new ArrayList<String>();
                        mails.add("raes.remy@gmail.com");
                        mails.add("sveviaurelien@gmail.com");
                        String message = input.getText().toString();

                        for (String s : mails){

                            Map<String, Object> mail = new HashMap<>();
                            mail.put("sujet", "[BETA] Retour de " + user.getMail());
                            mail.put("message", message);
                            mail.put("adresse", s);

                            String url = Configuration.SERVER + "/v1/userdb/send";
                            final com.android.volley.toolbox.JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(mail),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                VolleyLog.v("Response:%n %s", response.toString(4));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.e("Error: ", error.getMessage());
                                }
                            }) {

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("Authorization", "basic " + Base64.encodeToString((user.getMail() + ":" + user.getMdp()).getBytes(), Base64.NO_WRAP));
                                    return params;
                                }
                            };
                            queue.add(request);
                        }

                        Toast.makeText(getApplicationContext(), "Merci pour votre retour !", Toast.LENGTH_LONG).show();
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
