package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kancurzv on 23/03/16.
 */
public class JpeuxAiderActivity extends Activity  {


    private ListView mListView;
    private String[] listPhrases = new String[50];
    private String pmEnvoye;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpeuxaider);

        // Modification liste Rémy
        mListView = (ListView) findViewById(R.id.listView);
        for(int i=0; i<1; i++)
            items.add("Vive le Nutella !");


        Intent intent = getIntent();


        if(intent != null) {
            String message = intent.getStringExtra(HelpActivity.EXTRA_MESSAGE);
            if(message != null)
                items.add(message.toString());
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
        System.out.println("coucou");
        Intent i = new Intent( JpeuxAiderActivity.this, ChoiceActivity.class );
        startActivity(i);
    }


    // Bundle pour la sauvegarde
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("items",items);

    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        items = savedInstanceState.getStringArrayList("items");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                this.items);
        mListView.setAdapter(adapter);
    }


}