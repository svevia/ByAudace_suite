package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JpeuxAiderActivity est l'activité de consultation des demandes d'aide, où l'utilisateur est redirigé
 * après avoir sélectionné l'option adaptée sur l'activité ChoiceActivity.
 * L'activité ici présente permet à l'utilisateur de visualiser toutes les demandes postées par les utilisateurs
 * de l'application ; s'il se sent capable d'aider parmi l'une d'entre elles, il peut la sélectionner et se mettre
 * en contact avec la personne l'ayant postée (via l'envoi automatisé d'un mail).
 */
public class JpeuxAiderActivity extends Activity  {

    private ListView mListView;
    private ArrayAdapter<Phrase> adapter;
    private AlertDialog.Builder alertDialogBuilder;
    private static final String URL = Configuration.SERVER + "/v1/phrase";
    private List<Phrase> users;
    private Intent intent;
    private SwipeRefreshLayout swipeContainer;
    private RequestQueue queue;

    private ListAdapter adap;

    /**
     * La méthode onCreate surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsqu'une activité JpeuxAiderActivity est créée avec un Intent,
     * ou lorsque le terminal change d'orientation ; le bundle passé en paramètre permet alors
     * la sauvegarde des données.
     * Ici, elle initialise les attributs privés de la classe, récupère via une requête GET toutes
     * les demandes postées par le réseau des utilisateurs, et les injecte dans la liste de
     * l'application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_jpeuxaider);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // listener
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getConnexion();
                swipeContainer.setRefreshing(false);
            }
        });
        // Couleurs du logo de chargement
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mListView = (ListView) findViewById(R.id.listView);

        mListView = (ListView) findViewById(R.id.listView);
        users = new ArrayList<>();
        intent = this.getIntent();

        getConnexion();
    }

    /**
     * La méthode getConnexion réalise la connexion avec le serveur REST via une requête GET.
     * Elle passe ensuite la main à la méthode initComponent pour remplir la liste de couples phrase métier / besoin.
     */
    private void getConnexion(){

        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            byte[] u = json.toString().getBytes(
                                    "ISO-8859-1");
                            json = new String(u, "UTF-8");
                        } catch (UnsupportedEncodingException e ){
                            e.printStackTrace();
                        }
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

    /**
     * La méthode initComponent est appelée au moment de la réception des données (GET) par l'application.
     * Elle initialise ainsi le spinner de l'activité avec les couples phrase métier / besoin présents
     * sur le serveur.
     * Chaque couple reçoit également un listener permettant à l'utilisateur d'obtenir tous les détails
     * de celui-ci, mais également d'envoyer un mail de contact à l'auteur de la demande d'aide.
     */
    private void initComponent() {

        adap = new ListAdapter(this, android.R.layout.simple_list_item_1, users);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        mListView.setAdapter(adapter);

        /*
        View header = (View)getLayoutInflater().inflate(R.layout.list, null);
        mListView.addHeaderView(header);

        mListView.setAdapter(adap);
        */

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertNotification(android.R.drawable.ic_dialog_info, adapter.getItem(position).getMail(),
                        adapter.getItem(position).getBesoin() + "\n" + adapter.getItem(position).getPhrase(), position);
            }
        });
    }

    /**
     * La méthode onBackPressed surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsque l'utilisateur presse le bouton "retour" de
     * son terminal.
     * Ici, elle termine l'activité en cours, redirigeant l'utilisateur vers l'activité précédente.
     */
    @Override
    public void onBackPressed(){
        finish();
    }


    /**
     * La méthode alertNotification permet d'afficher une fenêtre de type "pop-up" au dessus de l'activité
     * en cours. Cette fenêtre possède un bouton de validation, qui appelle alertContact(), et un bouton de
     * retrait, qui ferme simplement la pop-up.
     *
     * @param icon Icône à afficher à gauche du titre de la fenêtre (Android.R.drawable.ic*)
     * @param title Texte à afficher en titre de la fenêtre
     * @param text Texte à afficher en tant que texte de corps de la fenêtre
     * @param position Paramètre indiquant la position de l'item dans la liste ; utile pour la redirection vers alertContact()
     */
    public void alertNotification(int icon, String title, String text, final int position){

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

    /**
     * La méthode alertContact permet à l'utilisateur consultant d'une demande d'aide d'entrer en contact
     * avec l'utilisateur l'ayant postée.
     * Redirige (actuellement) l'utilisateur vers une liste des applications qui permettent d'envoyer le
     * mail ; dans le futur, elle enverra directement le mail sans utilisation d'application tierce.
     *
     * @param position Paramètre passé par la méthode alertNotification, indiquant la position du couple
     *                 phrase métier / besoin concerné par cette notification dans la pile
     */
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

                        // redirection vers l'activité d'envoi du mail

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{adapter.getItem(position).getMail()});
                        i.putExtra(Intent.EXTRA_SUBJECT, "ByAudace : Demande de contact");
                        i.putExtra(Intent.EXTRA_TEXT   , "Bonjour "+adapter.getItem(position).getMail().split("@")[0]+",\n\n\n" +
                                "J'ai pris connaissance de votre besoin : \n\n" + adapter.getItem(position).getBesoin() + "\n\net vous propose mon aide afin de le résoudre.\n" +
                                "Merci de me contacter en retour de ce mail.\n\n\n" +
                                "Bonne journée !");
                        try {
                            startActivity(Intent.createChooser(i, "Envoi du mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(JpeuxAiderActivity.this, "Aucune application mail n'est installée.", Toast.LENGTH_SHORT).show();
                        }


                        // ----------------------------------------------------------------------------------------------------------------
                        // communication avec le serveur REST pour les stats

                        // id phrase, mail user
                        // post /v1/phrase/help
                        // ----------------------------------------------------------------------------------------------------------------

                        RequestQueue queue;
                        final String login = intent.getStringExtra("user_mail");
                        final String mdp = intent.getStringExtra("user_mot_de_passe");

                        Map<String, String> params = new HashMap<>();
                        params.put("phrase",position + "");
                        params.put("utilisateur",login);
                        //params.put("date", new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString());
                        Date date = new Date();
                        params.put("date",(new Timestamp(date.getTime())).toString());
                        //params.put("consultee", String.valueOf(0));

                        queue = Volley.newRequestQueue(getApplication().getApplicationContext());

                        String url = URL + "/help";
                        final com.android.volley.toolbox.JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params),
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
                                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                                System.out.println(params.toString());
                                return params;
                            }
                        };
                        queue.add(request);
                        // ----------------------------------------------------------------------------------------------------------------
                        // ----------------------------------------------------------------------------------------------------------------

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

    /**
     * La méthode buildUsersFromJason est appelée au moment de la réception des données (GET) par l'application.
     * Elle initialise la liste des utilisateurs présents sur le serveur.
     */
    private void buildUsersFromJson(String json) {
        final Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Phrase>>() {}.getType();
        users = gson.fromJson(json, listType);
    }

}