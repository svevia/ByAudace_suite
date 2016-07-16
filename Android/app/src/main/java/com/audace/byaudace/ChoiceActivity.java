package com.audace.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
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


        setContentView(R.layout.activity_choice);

        intent = this.getIntent();
        RequestQueue queue = Volley.newRequestQueue(this);

        // création utilisateur
        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/mail/"+intent.getStringExtra("user_mail"),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        System.out.println("Json : " + json);
                        String[] tok = json.split(",");

                        // A corriger : Les champs peuvent être nuls dans le User
                        user = new User(tok[0].split(":")[1], //digit
                                Integer.valueOf(tok[1].split(":")[1]), //id
                                tok[2].split(":")[1], //mail
                                tok[3].split(":")[1], //mdp
                                tok[4].split(":")[1], //nom
                                tok[5].split(":")[1], //numero
                                tok[6].split(":")[1]); //prenom
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
        intent.putExtra("id", user.getId());
        intent.putExtra("user_nom", user.getNom());
        intent.putExtra("user_prenom", user.getPrenom());
        intent.putExtra("user_numero", user.getNumero());
        intent.putExtra("user_mail", user.getMail());
        intent.putExtra("user_mot_de_passe", user.getMdp());
        startActivity(intent);
    }

}
