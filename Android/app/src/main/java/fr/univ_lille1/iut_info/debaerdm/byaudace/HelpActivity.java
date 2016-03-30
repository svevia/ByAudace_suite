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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.net.HttpHeaders;

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
        nbCharTxt2 = (TextView) findViewById(R.id.nbChar);

        phraseUne = new TextViewNbChar(phraseMetier, nbCharTxt);
        phraseUne.getTextView().addTextChangedListener(phraseUne);
        phraseDeux = new TextViewNbChar(phraseMetier, nbCharTxt);
        phraseDeux.getTextView().addTextChangedListener(phraseDeux);

        EditText tmp = phraseUne.getEditText();
        tmp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseUne.setEditText(tmp);
        tmp = phraseDeux.getEditText();
        tmp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        phraseDeux.setEditText(tmp);

        envoy = (Button) findViewById(R.id.button);

        intent = this.getIntent();
    }

    public void postPhrase(final View view){
        intent.setClass(HelpActivity.this, JpeuxAiderActivity.class);
        final String login = intent.getStringExtra("user_mail");
        final String mdp = intent.getStringExtra("user_mot_de_passe");

        System.out.println("EditText 1 :"+phraseUne.getEditText().toString());
        System.out.println("EditText 2 :"+phraseDeux.getEditText().toString());

        queue = Volley.newRequestQueue(this);

        final StringRequest request = new StringRequest(Request.Method.POST, Configuration.SERVER+"/v1/phrase",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {
                        System.out.println(json);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (null != error.networkResponse)
                {
                    Log.d("Volley Error Response", "" + error.networkResponse.statusCode);
                }
            }

        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("phrase", "test");//phraseUne.getEditText().getText().toString());
                params.put("besoin", "test");//phraseDeux.getEditText().getText().toString());
                params.put("mail", login);
                params.put("terminee", ""+false);
                params.put("consultee", ""+0);

                System.out.println("Param :"+params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                System.out.println(params.toString());
                return params;
            }
        };

        queue.add(request);

        /*startActivity(intent);
        this.finish();*/

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