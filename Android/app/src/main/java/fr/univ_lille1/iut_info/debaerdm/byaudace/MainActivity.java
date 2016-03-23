package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import frenchcomputerguy.rest.PostRequest;

public class MainActivity extends Activity {

    private static final String LOGIN[] = {"Toto", "Tutu", "Tata"};
    private static final String MDP[] = {"toto", "tutu", "tata"};
    private Button loginButton;
    private EditText loginText;
    private EditText passwordText;
    private PopupWindow errorMessage;
    private LinearLayout layout;
    private TextView tv;
    private PostRequest postRequest;
    /*private LayoutParams params;
    private LinearLayout mainLayout;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        postRequest = new PostRequest("http://httpbin.org/post", tmp);
        try{
            System.out.println("Requete REST : "+postRequest.getResponse().getJSONObject().getJSONObject("headers").getString("Test"));
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean ok = true;
        String login = ""+loginText.getText();
        String password = ""+passwordText.getText();
        if(LOGIN.length == MDP.length) {
            System.out.println("Test"+login+ " "+password);
            for (int i = 0; i < LOGIN.length; ++i){
                System.out.println(i);
                if (LOGIN[i].equals(login) && MDP[i].equals(password)){
                    System.out.println("Login success");
                    Intent activity = new Intent(MainActivity.this, ChoiceActivity.class);
                    startActivity(activity);
                    ok = true;
                    break;
                }
                else{
                    ok = false;
                }
            }
            System.out.println("OK : "+ok);
            if(!ok){
                errorMessage.showAsDropDown(layout,10,10);
                errorMessage.update(50, 50, 300, 80);
                errorMessage.setBackgroundDrawable(new DrawableContainer());
                errorMessage.setOutsideTouchable(true);
            }
        }
    }
}
