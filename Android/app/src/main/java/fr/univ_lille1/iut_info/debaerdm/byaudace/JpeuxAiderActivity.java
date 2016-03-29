package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

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
import java.util.List;

/**
 * Created by kancurzv on 23/03/16.
 */
public class JpeuxAiderActivity extends Activity  {

    private ListView mListView;

    private String[] listPhrases = new String[50];
    private String pmEnvoye;
    private EditText nbDem;

    private ArrayList<String> items = new ArrayList<>();




    //private ArrayList<String> items = new ArrayList<>();

    private ArrayAdapter<Phrase> adapter;

    private AlertDialog.Builder alertDialogBuilder;
    private final String URL = Configuration.SERVER + "/v1/phrase";
    private List<Phrase> users;
    private RequestQueue queue;

    HelpActivity help = new HelpActivity();
    String pmComplete="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_jpeuxaider);





        mListView = (ListView) findViewById(R.id.listView);


        items.add(" Mais au lieu de la simplicité,");
        items.add("Vive les chamallows !");
        items.add("Vive les sucettes !");
        items.add("Vive les croissants !");
        items.add("Vive les petits pains !");
        items.add("Vive les frites !");
    users = new ArrayList<>();

        queue = Volley.newRequestQueue(this);




        final Intent intent = getIntent();


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
                System.err.println(error.getMessage());
            }
        });


        queue.add(stringRequest);
    }


        if(intent != null) {

            String tmp="";
            String message = intent.getStringExtra(HelpActivity.EXTRA_MESSAGE);

            if (message != null){
                 pmComplete= message.toString();

                 if(pmComplete.length() >= 40) {
                    tmp = pmComplete.substring(0,40) +"...";
                 }
            }

                items.add(tmp.toString());

            

        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                this.items);

    private void initComponent(){
        System.out.println("Phrase create : "+users.toString());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);



        mListView.setAdapter(adapter);


         /*pmEnvoye = (String) getIntent().getSerializableExtra("sending");

        mListView = (ListView) findViewById(R.id.listView);

        for(int i=0; i < listPhrases.length; i++) {
            if(listPhrases[i].isEmpty()) {
                listPhrases[i] = pmEnvoye;
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(JpeuxAiderActivity.this,
                android.R.layout.simple_list_item_1, Integer.parseInt(pmEnvoye));
        mListView.setAdapter(adapter);*/




        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //alertNotification(view,android.R.drawable.ic_dialog_info, adapter.getItem(position).getMail(), adapter.getItem(position).getPhrase());
                alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                // set title
                alertDialogBuilder.setTitle("Contact");

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
                                i.putExtra(Intent.EXTRA_TEXT   , "Bonjour [prénomExemple],\n\n" +
                                        "J'ai pris connaissance de votre besoin : " + adapter.getItem(position).getBesoin() + " - " +
                                        adapter.getItem(position).getPhrase() +"\net vous propose mon aide afin de le résoudre.\n" +
                                        "Merci de me contacter en retour de ce mail.\n\n" +
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


                alertNotification(view,android.R.drawable.ic_dialog_info, adapter.getItem(position).getMail(), pmComplete);




            }
        });
    }


    /*private void showList() {
        if (users.isEmpty()) {
            textError.setText(getString(R.string.empty_list));
            textError.setVisibility(View.VISIBLE);
            listOfUsersView.setVisibility(View.GONE);
        } else {
            textError.setVisibility(View.GONE);
            listOfUsersView.setVisibility(View.VISIBLE);
        }
    }*/




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

    public void contact(View view){
        alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Contact");

        // set dialog message
        alertDialogBuilder
                .setMessage("Êtes-vous sûr de vouloir contacter cette personne ?")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // redirection vers l'envoi du mail

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"exemple@audace.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "ByAudace : Demande de contact");
                        i.putExtra(Intent.EXTRA_TEXT   , "Bonjour [prénomExemple],\n\n" +
                                "J'ai pris connaissance de votre besoin : [besoin] et vous propose mon aide afin de le résoudre.\n" +
                                "Merci de me contacter en retour de ce mail.\n\n" +
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

    // retour = redirection sur la page de choix


    public void alertNotification(View view, int icon, String title, String text){

        alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(text)
                .setIcon(icon)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    public void onBackPressed()
    {
        System.out.println("backbutton");
        //SavePreferences();

        super.onBackPressed();
    }



    private void buildUsersFromJson(String json) {
        final Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Phrase>>() {
        }.getType();
        users = gson.fromJson(json, listType);
    }

}