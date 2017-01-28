package com.audace.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * InfosActivity est l'activité d'informations, où l'utilisateur peut accéder à toutes les informations
 * qu'il a fournies à l'application (nom, prénom, adresse mail, numéro de téléphone et mot de passe) ; il
 * peut également les modifier depuis cette activité.
 */
public class InfosActivity extends Activity {

    private static final String URL = Configuration.SERVER + "/v1/userdb/editme";
    private Intent intent;
    private EditText nom;
    private EditText prenom;
    private EditText mail;
    private EditText numero;
    private EditText mdp1;
    private EditText mdp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_infos);

        intent = this.getIntent();
        nom = (EditText)findViewById(R.id.editTextNom);
        prenom = (EditText)findViewById(R.id.editTextPrenom);
        mail = (EditText)findViewById(R.id.editTextMail);
        numero = (EditText)findViewById(R.id.editTextNumero);
        mdp1 = (EditText)findViewById(R.id.editTextMdp1);
        mdp2 = (EditText)findViewById(R.id.editTextMdp2);

        nom.setText(intent.getStringExtra("user_nom"));
        prenom.setText(intent.getStringExtra("user_prenom"));
        mail.setText(intent.getStringExtra("user_mail"));
        numero.setText(intent.getStringExtra("user_numero"));
        mdp1.setText(intent.getStringExtra("user_mot_de_passe"));
        mdp2.setText(intent.getStringExtra("user_mot_de_passe"));
    }


    @Override
    public void onBackPressed() {
        if(intent.getBooleanExtra("first_use",false)){
            startActivity(new Intent(InfosActivity.this,MainActivity.class));
            finish();
        }else{
            finish();
        }
    }


    public void sendInfos(View view){
        if(nom.getText().toString().isEmpty() || prenom.getText().toString().isEmpty() || mail.getText().toString().isEmpty() ||
                numero.getText().toString().isEmpty() || mdp1.getText().toString().isEmpty() || mdp2.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis.", Toast.LENGTH_SHORT).show();

        }else if(!mdp1.getText().toString().equals(mdp2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
        }else{

            // On envoie le nouvel utilisateur
            RequestQueue queue = Volley.newRequestQueue(this);

            Map<String, Object> bonjour = new HashMap<>();
            bonjour.put("id",intent.getIntExtra("id",0));
            bonjour.put("digit",intent.getStringExtra("user_digit"));
            bonjour.put("mail",mail.getText().toString());
            bonjour.put("mot_de_passe",mdp2.getText().toString());
            bonjour.put("name",nom.getText().toString());
            bonjour.put("prenom",prenom.getText().toString());
            bonjour.put("numero",numero.getText().toString());
            bonjour.put("role","user");

            System.out.println("BATMAN : " + bonjour.toString());


            final com.android.volley.toolbox.JsonObjectRequest request = new JsonObjectRequest(URL, new JSONObject(bonjour),
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
                    params.put("Authorization", "basic " + Base64.encodeToString((intent.getStringExtra("user_mail") + ":" + intent.getStringExtra("user_mot_de_passe")).getBytes(), Base64.NO_WRAP));
                    return params;
                }
            };
            queue.add(request);

            startActivity(new Intent(InfosActivity.this,MainActivity.class));
            Toast.makeText(getApplicationContext(), "Vos informations ont bien été modifiées.\nVeuillez vous reconnecter afin qu'elles soient prises en compte.", Toast.LENGTH_LONG).show();
        }
    }
}
