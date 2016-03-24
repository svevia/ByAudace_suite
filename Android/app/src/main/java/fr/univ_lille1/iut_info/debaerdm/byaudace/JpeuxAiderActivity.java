package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.Window;
import android.view.WindowManager;
=======
import android.widget.EditText;
import android.widget.TextView;
>>>>>>> 3ecbcb8e1a41440f2fae203b0c7370a9a1cf35ec

/**
 * Created by kancurzv on 23/03/16.
 */
public class JpeuxAiderActivity extends Activity implements TextWatcher {
    private EditText status;
    private TextView nbCharTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_jpeuxaider);

        status = (EditText) findViewById(R.id.phrase);
        status.addTextChangedListener(this);
        nbCharTxt = (TextView) findViewById(R.id.textView6);
        nbCharTxt.setTextColor(Color.GREEN);

        int maxLength = 300;
        status.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});



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
}