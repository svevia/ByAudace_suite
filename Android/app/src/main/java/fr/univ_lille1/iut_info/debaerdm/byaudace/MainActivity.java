package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.frenchcomputerguy.rest.Request;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String LOGIN[] = {"","Toto", "Tutu", "Tata"};
    private static final String MDP[] = {"","toto", "tutu", "tata"};
    private Button loginButton;
    private EditText loginText;
    private EditText passwordText;
    private PopupWindow errorMessage;
    private LinearLayout layout;
    private TextView tv;
    private Request request;
    /*private LayoutParams params;
    private LinearLayout mainLayout;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        request = new Request("http://172.18.49.88:8080");
        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.LogButton);
        loginText = (EditText) findViewById(R.id.LoginText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        errorMessage = new PopupWindow(this);
        layout = new LinearLayout(this);
        tv = new TextView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onChangeActivity(View view){
        Map<String, String> tmp = new HashMap<String, String>();
        tmp.put("Test", "This is a test");
        boolean logged = true;

        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();

        //request.GET("GET");
        if(LOGIN.length == MDP.length) {

            for (int i = 0; i < LOGIN.length; ++i){

                if (LOGIN[i].equals(login) && MDP[i].equals(password)){

                    System.out.println("Login success");
                    Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                    startActivity(activity);
                    logged = true;
                    break;
                }
                else{
                    logged = false;
                }
            }

            if(!logged){
                /*

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this.getApplicationContext());

                // set title
                alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                */


            }
        }
    }
}
