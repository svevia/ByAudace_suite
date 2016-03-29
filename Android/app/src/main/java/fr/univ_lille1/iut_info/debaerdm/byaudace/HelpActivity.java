package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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

/**
 * Created by kancurzv on 23/03/16.
 */
public class HelpActivity extends Activity implements TextWatcher {

    private EditText pm;
    private Button envoy;

    private final String URL = Configuration.SERVER + "/v1/phrase";
    private int i=0;

    private EditText status;
    private TextView nbCharTxt;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_help);


        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter. createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item) ;
        spin.setAdapter(adapter);

        status = (EditText) findViewById(R.id.pm);
        status.addTextChangedListener(this);
        nbCharTxt = (TextView) findViewById(R.id.nbChar);
        nbCharTxt.setTextColor(Color.GREEN);

        int maxLength = 300;
        status.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        envoy = (Button) findViewById(R.id.button);

    }

    public void postPhrase(View view){

        Intent i = new Intent( HelpActivity.this, JpeuxAiderActivity.class );
        //i.putExtra(EXTRA_MESSAGE, pm.getText().toString());
        startActivity(i);
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int nbChar = status.getText().toString().length();
        int leftChar = 300 - nbChar;
        String restant = "";

        if(leftChar == 0 || leftChar == 1){
            restant = " caractère restant.";
        }else{
            restant = " caractères restants.";
        }

        nbCharTxt.setText(Integer.toString(leftChar) + restant);
        nbCharTxt.setTextColor(Color.GREEN);

        if (leftChar < 50 && leftChar >= 11) {
            nbCharTxt.setTextColor(Color.rgb(255,165,0));
        }else if (leftChar <= 10 && leftChar >= 0) {
            nbCharTxt.setTextColor(Color.RED);
        }


    }


    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    public int getI(){
        return this.i;
    }

    public EditText getPm(){
        return this.pm;
    }

}