package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayAdapter<String> adapter;
    private AlertDialog.Builder alertDialogBuilder;


    HelpActivity help = new HelpActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_jpeuxaider);





        mListView = (ListView) findViewById(R.id.listView);
        for(int i=0; i<1; i++)
            items.add("Vive le Nutella !");

        final Intent intent = getIntent();
        String[]   myStringArray= new String[5];
        myStringArray[1]= "a";


        if(intent != null) {
            String  message = intent.getStringExtra(HelpActivity.EXTRA_MESSAGE);
            if(message != null)
                items.add(message.toString());
            adapter.notifyDataSetChanged();
            saveStringToPreferences(message.toString());
        }



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                this.items);
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

    }



    private void saveStringToPreferences(String str){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("label", str);
        editor.apply();
    }


    public void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String previousText = preferences.getString("label", "");
        if(! TextUtils.isEmpty(previousText)){
            items.add(help.getPm().getText().toString());
            adapter.notifyDataSetChanged();
        }
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

    public void contact(View view){
        alertDialogBuilder = new AlertDialog.Builder(
                this);

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
    @Override
    public void onBackPressed(){
        finish();
    }

    public ArrayList getItems(){
        return this.items;
    }

    public ArrayAdapter<String> getAdaptater(){
        return this.adapter;
    }
}