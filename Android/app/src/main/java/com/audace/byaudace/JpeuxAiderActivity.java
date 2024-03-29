package com.audace.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    private PhraseAdapter adapter;
    private AlertDialog.Builder alertDialogBuilder;
    private static final String URL = Configuration.SERVER + "/v1/phrase";
    private List<Phrase> phrases;
    private Intent intent;
    private SwipeRefreshLayout swipeContainer;
    private RequestQueue queue;
    private int history = -4242;
    private User user;
    private User poster;
    private LinearLayout test;
    private View report;
    private LinearLayout page_buttons;

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
                history = -4242;
                swipeContainer.setRefreshing(false);
            }
        });
        // Couleurs du logo de chargement
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);


        mListView = (ListView) findViewById(R.id.listView);
        phrases = new ArrayList<>();
        intent = this.getIntent();

        queue = Volley.newRequestQueue(getApplication().getApplicationContext());

        page_buttons = (LinearLayout)findViewById(R.id.page_buttons);
        page_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                            byte[] u = json.getBytes("ISO-8859-1");
                            json = new String(u, "UTF-8");

                        } catch (UnsupportedEncodingException e ){
                            e.printStackTrace();
                        }
                        buildPhrasesFromJson(json);
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
                params.put("Authorization", "basic " + Base64.encodeToString((intent.getStringExtra("user_mail") + ":" + intent.getStringExtra("mdp")).getBytes(), Base64.NO_WRAP));
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

        adapter = new PhraseAdapter(this,
                R.layout.listview_item_row, phrases);

        mListView.setAdapter(adapter);

        // Notifications de contact
        mListView.setLongClickable(true);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                poster = getUser(adapter.getItem(position).getIdUser(),
                        adapter.getItem(position),
                        intent.getStringExtra("user_mail"),
                        intent.getStringExtra("user_mot_de_passe"));
                        */
                alertSignalement(adapter.getItem(position));
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                LinearLayout test = (LinearLayout) mListView.getChildAt(position - mListView.getFirstVisiblePosition());
                View report = test.findViewById(R.id.imgReport);
                final View category = test.findViewById(R.id.imgIcon);

                    report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //alertSignalement(adapter.getItem(position));
                            getUser(adapter.getItem(position).getIdUser(),
                                    adapter.getItem(position),
                                    intent.getStringExtra("user_mail"),
                                    intent.getStringExtra("user_mot_de_passe"));
                        }
                    });

                category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "Catégorie : ", Toast.LENGTH_SHORT).show();
                    }
                });

                // On déroule la phrase voulue, si une autre était ouverte, elle est fermée.
                if(history == -4242){
                    ouvrirPhrase(position);
                    history = position;
                }else{
                    if(history == position){
                        fermerPhrase(history);
                        history = -4242;
                    }else {
                        fermerPhrase(history);
                        ouvrirPhrase(position);
                        history = position;
                    }
                }


            }
        });
    }

    public void ouvrirPhrase(int position){
        test = (LinearLayout) mListView.getChildAt(position - mListView.getFirstVisiblePosition());
        report = test.findViewById(R.id.imgReport);
        // getChildAt renvoie une position qui est relative au nombre d'éléments visibles sur l'écran,
        // et non à la position de la phrase dans la liste.

        // La fonction de signalement devient visible
        report.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams lp = test.getLayoutParams();
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                350,
                getResources().getDisplayMetrics());
        test.setLayoutParams(lp);
        adapter.getItem(position).setDeroulee(true);
    }

    public void fermerPhrase(int position){
        // La fonction de signalement est cachée
        report.setVisibility(View.GONE);

        ViewGroup.LayoutParams lp = test.getLayoutParams();
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                80,
                getResources().getDisplayMetrics());
        test.setLayoutParams(lp);
        adapter.getItem(position).setDeroulee(false);
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
     * La méthode alertContact permet à l'utilisateur consultant d'une demande d'aide d'entrer en contact
     * avec l'utilisateur l'ayant postée.
     * Redirige (actuellement) l'utilisateur vers une liste des applications qui permettent d'envoyer le
     * mail ; dans le futur, elle enverra directement le mail sans utilisation d'application tierce.
     */
    public void alertContact(final Phrase popeye, final User bonjour){

        alertDialogBuilder = new AlertDialog.Builder(this);

        final String login = intent.getStringExtra("user_mail");
        final String mdp = intent.getStringExtra("user_mot_de_passe");
        final String nom = intent.getStringExtra("user_nom");
        final String prenom = intent.getStringExtra("user_prenom");
        final int id_user = intent.getIntExtra("id",0);

        // set title
        alertDialogBuilder.setTitle("Contact : " + bonjour.getPrenom() + " " + bonjour.getNom());

        // set dialog message
        alertDialogBuilder
                .setMessage("Êtes-vous sûr de vouloir contacter cette personne ?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String message = "Bonjour " + bonjour.getPrenom() +",<br><br><br>" +
                                "Mon nom est " + prenom + " " + nom + ", j'ai pris connaissance de votre besoin : <br><br>" + popeye.getBesoin() + "<br><br>et vous propose mon aide afin de le résoudre.<br>" +
                                "Merci de me contacter en retour de ce mail (mon adresse est la suivante : " +  login + ").<br><br><br>" +
                                "Bonne journée !";

                        String htmlwow = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                "\t<head>\n" +
                                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; />\n" +
                                "\t\t<title>Demande de contact !</title>\n" +
                                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                                "\t</head>\n" +
                                "\t\n" +
                                "\t\n" +
                                "\t<body style=\"margin: 0; padding: 20px 0 20px 0;\">\n" +
                                "\n" +
                                "\t\t<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: #a8a9ab;\">\n" +
                                "\t\t\t<tr>\n" +
                                "\t\t\t\t<td align=\"center\" style=\"padding: 20px 0 20px 0;\">\n" +
                                "\t\t\t\t\t<img src=\"http://www.campus-audace.com/images/logo.gif\" alt=\"Audace logo\" style=\"display: block;\" />\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t</tr>\n" +
                                "\n" +
                                "\t\t\t<tr>\n" +
                                "\t\t\t\t<td style=\"padding: 5px 0 5px 30px;\">Mise en relation ByAudace !</td>\n" +
                                "\t\t\t</tr>\n" +
                                "\t\t\t<tr>\n" +
                                "\t\t\t\t<td style=\"padding: 20px 30px 30px 30px;\">\n" +
                                "\t\t\t\t\tBonjour <i>" + bonjour.getPrenom() + "</i>,<br /><br />\n" +
                                "\t\t\t\t\tJe m'appelle <i>" + prenom + " " + nom + "</i>, j'ai pris connaissance de votre besoin  <br />\n";

                        if(!popeye.getBesoin().equals(""))
                                htmlwow += "\t\t\t\t\t<b>" + popeye.getBesoin() + "</b><br />\n";

                        htmlwow += "\t\t\t\t\tet vous propose mon aide afin de le résoudre.<br /><br />\n\n" +
                                "\t\t\t\t\tMerci de me contacter à l'adresse suivante : <a href=\"mailto:" + login + "\">" + login + "</a>.<br /><br />\n" +
                                "\t\t\t\t\tBonne journée !<br />\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t</tr>\n" +
                                "\t\t\t<tr>\n" +
                                "\t\t\t\t<td bgcolor=\"#a8a9ab\" style=\"padding: 5px 0 5px 30px; color: #FFFFFF; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                                "\t\t\t\t\t&reg; Audace, 2016<br/>\n" +
                                "\t\t\t\t\t<font color=\"#FFFFFF\"><a href=\"#\" style=\"color: #FFFFFF;\">Signaler cet utilisateur</a>\n" +
                                "\t\t\t\t\t<i>Ceci est un mail automatique, merci de ne pas y répondre.</i></font>\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t</tr>\n" +
                                "\t\t</table>\n" +
                                "\t\t\n" +
                                "\t</body>\n" +
                                "\n" +
                                "\n" +
                                "</html>";

                        Map<String, Object> mail = new HashMap<>();
                        mail.put("sujet","Mise en relation ByAudace");
                        mail.put("message",htmlwow);
                        mail.put("adresse", bonjour.getMail());

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
                        }){

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                                //System.out.println(params.toString());
                                return params;
                            }
                        };
                        queue.add(request);


                        // ----------------------------------------------------------------------------------------------------------------
                        // communication avec le serveur REST pour les stats
                        // ----------------------------------------------------------------------------------------------------------------

                        Map<String, Object> params = new HashMap<>();
                        params.put("phrase",popeye.getId() + "");
                        params.put("utilisateur",id_user);
                        Date date = new Date();
                        params.put("date",(new Timestamp(date.getTime())).toString());


                        url = URL + "/help";
                        final com.android.volley.toolbox.JsonObjectRequest request2 = new JsonObjectRequest(url, new JSONObject(params),
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
                                //System.out.println(params.toString());
                                return params;
                            }
                        };
                        queue.add(request2);
                        // ----------------------------------------------------------------------------------------------------------------
                        // ----------------------------------------------------------------------------------------------------------------
                        
                        Toast.makeText(getApplicationContext(), "Demande de contact envoyée.", Toast.LENGTH_SHORT).show();
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
     * La méthode alertSignalement permet aux utilisateurs de signaler une phrase qui ne serait pas
     * en accord avec les règles de publication de contenu (racisme, incitation à la haine...).
     * Le signalement de la phrase est visible depuis le panel d'administration.
     */
    public void alertSignalement(final Phrase phrase){

        final String login = intent.getStringExtra("user_mail");
        final String mdp = intent.getStringExtra("user_mot_de_passe");

        alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Signalement");

        // set dialog message
        alertDialogBuilder
                .setMessage("Êtes-vous sûr de vouloir signaler cette phrase ?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"/signalement/" + phrase.getId(),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String json) {
                                        try {
                                            byte[] u = json.getBytes("ISO-8859-1");
                                            json = new String(u, "UTF-8");
                                        } catch (UnsupportedEncodingException e ){
                                            e.printStackTrace();
                                        }
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
                                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                                return params;
                            }
                        };

                        queue.add(stringRequest);

                        Toast.makeText(getApplicationContext(), "Signalement effectué.", Toast.LENGTH_SHORT).show();
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
     * Elle initialise la liste des couples phrase métier / besoin dans l'application.
     */
    private void buildPhrasesFromJson(String json) {

        final Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Phrase>>() {}.getType();
        phrases = gson.fromJson(json, listType);

        for(Phrase p : phrases){
            System.out.println("Phrase : " + p.getId() + " " + p.getIdUser());
        }
    }


    private User getUser(int id, final Phrase popeye, final String login, final String mdp){

        queue = Volley.newRequestQueue(this);

        final StringRequest request = new StringRequest(Request.Method.GET, Configuration.SERVER+"/v1/userdb/" + id,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String json) {

                        JSONObject obj = null;
                        User coucou = null;

                        try {
                            byte[] u = json.getBytes("ISO-8859-1");
                            json = new String(u, "UTF-8");

                            obj = new JSONObject(json);
                            System.out.println("JSONOBJ : " + obj.toString());
                            coucou = new User(obj.getInt("id"),
                                    obj.getString("mail"),
                                    obj.getString("mot_de_passe"),
                                    obj.getString("name"),
                                    obj.getString("prenom"),
                                    obj.getString("numero"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        alertContact(popeye,coucou);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("ERROR : " + error.getMessage());
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "basic " + Base64.encodeToString((login + ":" + mdp).getBytes(), Base64.NO_WRAP));
                return params;
            }
        };
        queue.add(request);

        return user;
    }

}