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
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kancurzv on 23/03/16.
 */
public class
        HelpActivity extends Activity implements TextWatcher {
    EditText pm;
    Button envoy;
    public final static  String EXTRA_MESSAGE="0";

    private EditText status;
    private TextView nbCharTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_help);



        status = (EditText) findViewById(R.id.phrase);
        status.addTextChangedListener(this);
        nbCharTxt = (TextView) findViewById(R.id.textView6);
        nbCharTxt.setTextColor(Color.GREEN);

        int maxLength = 300;
        status.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        //--------recuperation pm

        pm = (EditText) findViewById(R.id.phrase);
        envoy = (Button) findViewById(R.id.button);

        envoy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*String valStr = pm.getText().toString();
                Intent intent = new Intent(HelpActivity.this, JpeuxAiderActivity.class);
                intent.putExtra("sending", valStr);
                startActivity(intent);*/


                Intent i1 = new Intent( HelpActivity.this, JpeuxAiderActivity.class );
                i1.putExtra(EXTRA_MESSAGE, pm.getText().toString());
                startActivityForResult(i1, 0);

            }

        });




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
        nbCharTxt.setText(Integer.toString(leftChar) + " caracteres restant");
        nbCharTxt.setTextColor(Color.GREEN);
        if (leftChar < 50 && leftChar >= 11)
            nbCharTxt.setTextColor(Color.YELLOW);
        else if (leftChar <= 10 && leftChar >= 0) {
            nbCharTxt.setTextColor(Color.RED);
        }


    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        //valStr = pm.getText().toString();
                /*Intent intent = new Intent(HelpActivity.this, JpeuxAiderActivity.class);
                intent.putExtra("string", valStr);
                startActivity(intent);*/

    }


}