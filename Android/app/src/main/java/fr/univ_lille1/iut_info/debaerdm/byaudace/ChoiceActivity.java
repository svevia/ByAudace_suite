package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ChoiceActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_choice);

        intent = this.getIntent();
    }

    // retour = redirection sur la page de login
    @Override
    public void onBackPressed(){
        Intent i = new Intent( ChoiceActivity.this, MainActivity.class );
        startActivity(i);
        this.finish();
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


    public void onClick2(View v) {
        intent.setClass(this, HelpActivity.class);
        intent.putExtra("user_nom", intent.getStringExtra("user_nom"));
        intent.putExtra("user_prenom", intent.getStringExtra("user_prenom"));
        intent.putExtra("user_numero", intent.getStringExtra("user_numero"));
        intent.putExtra("user_mail", intent.getStringExtra("user_mail"));
        intent.putExtra("user_mot_de_passe", intent.getStringExtra("user_mot_de_passe"));
        startActivity(intent);
    }

    public void onClick(View v) {
        intent.setClass(this, JpeuxAiderActivity.class);
        intent.putExtra("user_nom", intent.getStringExtra("user_nom"));
        intent.putExtra("user_prenom", intent.getStringExtra("user_prenom"));
        intent.putExtra("user_numero", intent.getStringExtra("user_numero"));
        intent.putExtra("user_mail", intent.getStringExtra("user_mail"));
        intent.putExtra("user_mot_de_passe", intent.getStringExtra("user_mot_de_passe"));
        startActivity(intent);
    }

}
