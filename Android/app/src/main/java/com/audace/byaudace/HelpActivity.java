package com.audace.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * HelpActivity est l'activité de demande d'aide, où l'utilisateur est redirigé après avoir sélectionné
 * l'option adaptée sur l'activité ChoiceActivity.
 * L'activité ici présente permet à l'utilisateur de formuler sa demande sous la forme d'une phrase métier
 * et d'un besoin, ainsi que d'une catégorie de besoin.
 * Une fois formulée, la demande peut être envoyée sur le serveur.
 */
public class HelpActivity extends Activity{

    private static final String URL = Configuration.SERVER + "/v1/phrase";
    private static final int MAX_LENGTH= 300;
    private Intent intent;
    private TextViewNbChar phraseUne;
    private TextViewNbChar phraseDeux;
    private Spinner categorie;
    private EditText phraseMetier;
    private EditText phraseBesoin;
    private TextView nbCharTxt;
    private TextView nbCharTxt2;

    /**
     * La méthode onCreate surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsqu'une activité HelpActivity est créée avec un Intent,
     * ou lorsque le terminal change d'orientation ; le bundle passé en paramètre permet alors
     * la sauvegarde des données.
     * Elle est ici utilisée pour initialiser tous les attributs privés de la classe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_help);


        categorie = (Spinner) findViewById(R.id.spinner);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner, R.layout.spinner_item);
        ArrayAdapter adapter = ArrayAdapter. createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item) ;
        categorie.setAdapter(adapter);

        phraseMetier = (EditText) findViewById(R.id.pm);
        phraseBesoin = (EditText) findViewById(R.id.phrase);
        nbCharTxt = (TextView) findViewById(R.id.nbChar);
        nbCharTxt2 = (TextView) findViewById(R.id.nbChar2);

        phraseUne = new TextViewNbChar(phraseMetier, nbCharTxt,300);
        phraseDeux = new TextViewNbChar(phraseBesoin, nbCharTxt2,100);

        EditText tmp = phraseUne.getEditText();
        tmp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseUne.setEditText(tmp);

        EditText tmp2 = phraseDeux.getEditText();
        tmp2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseDeux.setEditText(tmp2);

        intent = this.getIntent();
    }

    /**
     * La méthode postPhrase, appelée par défaut lorsque l'utilisateur presse le bouton "Envoyer",
     * envoie sa demande sur le serveur via une requête POST.
     * Elle opère quelques traitements sur les différents champs envoyés au serveur, afin d'éviter
     * de compromettre le fonctionnement du système.
     */
    public void postPhrase(final View view){
        RequestQueue queue;
        intent.setClass(HelpActivity.this, JpeuxAiderActivity.class);
        final int id = intent.getIntExtra("id",0);
        final String mail = intent.getStringExtra("user_mail");
        final String mdp = intent.getStringExtra("user_mot_de_passe");

        phraseMetier.setBackgroundColor(Color.WHITE);
        phraseBesoin.setBackgroundColor(Color.WHITE);

        if(!phraseUne.getEditText().getText().toString().equals("") && !phraseDeux.getEditText().getText().toString().equals("")) {

            Map<String, Object> params = new HashMap<>();
            // .replaceAll("[`~!@#$%^&*()_|+\\-=?;:\'\"/<>]", "")
            params.put("phrase", TextUtils.htmlEncode(phraseUne.getEditText().getText().toString()));
            params.put("besoin", phraseDeux.getEditText().getText().toString());
            params.put("id_user", id);
            params.put("terminee", String.valueOf(false));
            params.put("categorie",categorie.getSelectedItemPosition());
            params.put("signalement",0);

            Date date = new Date();
            params.put("date",(new Timestamp(date.getTime())).toString());

            queue = Volley.newRequestQueue(this);

            final com.android.volley.toolbox.JsonObjectRequest request = new JsonObjectRequest(URL, new JSONObject(params),
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
                    params.put("Authorization", "basic " + Base64.encodeToString((mail + ":" + mdp).getBytes(), Base64.NO_WRAP));
                    return params;
                }
            };
            queue.add(request);
            startActivity(intent);
            this.finish();

        }else{
            if(phraseUne.getEditText().getText().toString().equals(""))
                phraseMetier.setBackgroundColor(Color.RED);

            if(phraseDeux.getEditText().getText().toString().equals(""))
                phraseBesoin.setBackgroundColor(Color.RED);


        }

    }

}