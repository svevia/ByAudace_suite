package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.common.net.HttpHeaders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kancurzv on 23/03/16.
 */
public class HelpActivity extends Activity{

    private Button envoy;

    private final String URL = Configuration.SERVER + "/v1/phrase";
    private final int MAX_LENGTH= 300;

    private EditText phraseMetier;
    private EditText phraseBesoin;
    private TextView nbCharTxt;
    private TextView nbCharTxt2;
    private Spinner spin;
    private Intent intent;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private boolean ok;
    private Phrase phrase;
    private TextViewNbChar phraseUne;
    private TextViewNbChar phraseDeux;

    private TextView textViewUn;
    private EditText editTextUn;
    private TextView textViewDeux;
    private EditText editTextDeux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_help);


        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter. createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item) ;
        spin.setAdapter(adapter);

        phraseMetier = (EditText) findViewById(R.id.pm);
        phraseBesoin = (EditText) findViewById(R.id.phrase);
        nbCharTxt = (TextView) findViewById(R.id.nbChar);
        nbCharTxt2 = (TextView) findViewById(R.id.nbChar2);


        /*phraseMetier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int nbChar = phraseMetier.getText().toString().length();
                int leftChar = 300 - nbChar;
                String restant = "";

                if (leftChar == 0 || leftChar == 1) {
                    restant = " caractère restant.";
                } else {
                    restant = " caractères restants.";
                }

                nbCharTxt.setText(Integer.toString(leftChar) + restant);
                nbCharTxt.setTextColor(Color.GREEN);

                if (leftChar < 50 && leftChar >= 11) {
                    nbCharTxt .setTextColor(Color.rgb(255, 165, 0));
                } else if (leftChar <= 10 && leftChar >= 0) {
                    nbCharTxt .setTextColor(Color.RED);
                }

            }
        });

        phraseMetier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int nbChar = phraseBesoin.getText().toString().length();
                int leftChar = 300 - nbChar;
                String restant = "";

                if (leftChar == 0 || leftChar == 1) {
                    restant = " caractère restant.";
                } else {
                    restant = " caractères restants.";
                }

                nbCharTxt2.setText(Integer.toString(leftChar) + restant);
                nbCharTxt2.setTextColor(Color.GREEN);

                if (leftChar < 50 && leftChar >= 11) {
                    nbCharTxt2.setTextColor(Color.rgb(255, 165, 0));
                } else if (leftChar <= 10 && leftChar >= 0) {
                    nbCharTxt2.setTextColor(Color.RED);
                }

            }
        });

        phraseMetier.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseBesoin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});*/


        phraseUne = new TextViewNbChar(phraseMetier, nbCharTxt);
        phraseDeux = new TextViewNbChar(phraseBesoin, nbCharTxt2);

        EditText tmp = phraseUne.getEditText();
        tmp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseUne.setEditText(tmp);

        EditText tmp2 = phraseDeux.getEditText();
        tmp2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseDeux.setEditText(tmp2);


        envoy = (Button) findViewById(R.id.button);

        intent = this.getIntent();
    }

    public void postPhrase(final View view){
        intent.setClass(HelpActivity.this, JpeuxAiderActivity.class);
        final String login = intent.getStringExtra("user_mail");
        final String mdp = intent.getStringExtra("user_mot_de_passe");

        Map<String, String> params = new HashMap<>();
        params.put("phrase", phraseUne.getEditText().getText().toString());//phraseUne.getEditText().getText().toString());
        params.put("besoin", phraseDeux.getEditText().getText().toString());//phraseDeux.getEditText().getText().toString());
        params.put("mail", login);
        params.put("terminee", String.valueOf(false));
        params.put("consultee", String.valueOf(0));

        queue = Volley.newRequestQueue(this);

        final com.android.volley.toolbox.JsonObjectRequest request = new JsonObjectRequest(Configuration.SERVER+"/v1/phrase", new JSONObject(params),
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
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            //params.put("Content-Type","application/json");
            params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
            System.out.println(params.toString());
            return params;
            }
        };

        queue.add(request);

        startActivity(intent);
        this.finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choise, menu);
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


    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}