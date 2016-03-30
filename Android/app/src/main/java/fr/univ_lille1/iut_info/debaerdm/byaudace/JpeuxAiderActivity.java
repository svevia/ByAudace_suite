package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kancurzv on 23/03/16.
 */
public class JpeuxAiderActivity extends Activity  {

    private ListView mListView;

    private String[] listPhrases = new String[50];
    private String pmEnvoye;
    private EditText nbDem;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<Phrase> adapter;
    private AlertDialog.Builder alertDialogBuilder;
    private final String URL = Configuration.SERVER + "/v1/phrase";
    private List<Phrase> users;
    private RequestQueue queue;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_jpeuxaider);
        mListView = (ListView) findViewById(R.id.listView);

        mListView = (ListView) findViewById(R.id.listView);
        users = new ArrayList<>();
        intent = this.getIntent();

        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        buildUsersFromJson(json);
                        initComponent();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.err.println(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "basic " + Base64.encodeToString((intent.getStringExtra("user_mail") + ":" + intent.getStringExtra("user_mot_de_passe")).getBytes(), Base64.NO_WRAP));
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void initComponent(){

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertNotification(view, android.R.drawable.ic_dialog_info, adapter.getItem(position).getMail(),
                        adapter.getItem(position).getBesoin()+"\n"+adapter.getItem(position).getPhrase(), position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choice, menu);
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

    // retour = redirection sur la page de choix
    @Override
    public void onBackPressed(){
        finish();
    }


    public void alertNotification(View view, int icon, String title, String text, final int position){

        alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(text)
                .setIcon(icon)
                .setCancelable(false)
                .setPositiveButton("Contacter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertContact(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void alertContact(final int position){

        alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Contact : " + adapter.getItem(position).getMail());

        // set dialog message
        alertDialogBuilder
                .setMessage("Êtes-vous sûr de vouloir contacter cette personne ?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // redirection vers l'envoi du mail

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{adapter.getItem(position).getMail()});
                        i.putExtra(Intent.EXTRA_SUBJECT, "ByAudace : Demande de contact");
                        i.putExtra(Intent.EXTRA_TEXT   , "Bonjour [prénomExemple],\n\n\n" +
                                "J'ai pris connaissance de votre besoin : \n\n" + adapter.getItem(position).getBesoin() + "\n\net vous propose mon aide afin de le résoudre.\n" +
                                "Merci de me contacter en retour de ce mail.\n\n\n" +
                                "Bonne journée !");
                        try {
                            startActivity(Intent.createChooser(i, "Envoi du mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(JpeuxAiderActivity.this, "Aucune application mail n'est installée.", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void buildUsersFromJson(String json) {
        final Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Phrase>>() {
        }.getType();
        users = gson.fromJson(json, listType);
    }
}