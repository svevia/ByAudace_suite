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

/**
 * Created by kancurzv on 23/03/16.
 */
public class JpeuxAiderActivity extends Activity  {


    ListView mListView;
    String[] listPhrases = new String[50];
    String pmEnvoye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_jpeuxaider);

        final Intent intent = getIntent();
        String message = intent.getStringExtra(HelpActivity.EXTRA_MESSAGE);
        final TextView tv1 = (TextView)findViewById( R.id.textView3 );
        tv1.setText(message);




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


}