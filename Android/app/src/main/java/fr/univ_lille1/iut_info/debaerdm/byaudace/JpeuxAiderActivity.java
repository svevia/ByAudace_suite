package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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
    private EditText nbDem;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;


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
       /* for(int i = 0 ; i < myStringArray.length; i++){
            if(myStringArray[i].isEmpty()){
                myStringArray[i]= myStringArray + 
            }
        }*/




/*
        String[]   myStringArray={intent.getStringExtra(HelpActivity.EXTRA_MESSAGE),"B","C"};

>>>>>>> 9563b7ab6a7b65fcc43c050882ebcc0acfeb9899

                ArrayAdapter<String> myAdapter = new
                        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);

                ListView myList = (ListView) findViewById(R.id.listView);
                myList.setAdapter(myAdapter);


            final TextView tv1 = (TextView)findViewById( R.id.textView3 );
        //tv1.setText(message);
        */


        if(intent != null) {
            String  message = intent.getStringExtra(HelpActivity.EXTRA_MESSAGE);
            if(message != null)
                items.add(message.toString());
                this.getAdaptater().notifyDataSetChanged();
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
        finish();
    }

    public ArrayList getItems(){
        return this.items;
    }

    public ArrayAdapter<String> getAdaptater(){
        return this.adapter;
    }
}