package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * ChoiceActivity est l'activité de choix, où l'utilisateur est redirigé après s'être authentifié avec succès.
 * Celle-ci propose deux options :
 * "HELP", où l'utilisateur est redirigé vers l'activité HelpActivity afin de poster sa phrase ;
 * "J'PEUX AIDER", où l'utilisateur est redirigé vers l'activité JpeuxAiderActivity afin de consulter les phrases métier.
 */
public class ChoiceActivity extends Activity {

    private Intent intent;

    /**
     * La méthode onCreate surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsqu'une activité ChoiceActivity est créée avec un Intent,
     * ou lorsque le terminal change d'orientation ; le bundle passé en paramètre permet alors
     * la sauvegarde des données.
     */
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

    /**
     * La méthode onBackPressed surcharge la méthode du même nom dans la classe mère Activity.
     * Elle est appelée automatiquement lorsque l'utilisateur presse le bouton "retour" de
     * son terminal.
     * Ici, appeler cette méthode tue l'activité en fonctionnement et redirige l'utilisateur
     * sur la page de login.
     */
    @Override
    public void onBackPressed(){

        // redirection sur la page de login
        Intent i = new Intent( ChoiceActivity.this, MainActivity.class );
        startActivity(i);
        this.finish();
    }

    /**
     * La méthode helpme est par défaut appelée lorsque l'utilisateur appuie sur le bouton de demande d'aide
     * ("HELP"). Celle-ci redirige l'utilisateur vers l'activité HelpActivity, tout en lui passant grâce à
     * l'intent les données nécessaires au maintien de la connexion de sa session.
     */
    public void helpme(View v) {
        intentClass(HelpActivity.class);
    }

    /**
     * La méthode aider est par défaut appelée lorsque l'utilisateur appuie sur le bouton de consultation
     * des demandes d'aide ("J'PEUX AIDER"). Celle-ci redirige l'utilisateur vers l'activité JpeuxAiderActivity,
     * tout en lui passant grâce à l'intent les données nécessaires au maintien de la connexion de sa session.
     */
    public void aider(View v) {
        intentClass(JpeuxAiderActivity.class);
    }

    public void intentClass(Class changeClass){
        intent.setClass(this, changeClass);
        intent.putExtra("user_nom", intent.getStringExtra("user_nom"));
        intent.putExtra("user_prenom", intent.getStringExtra("user_prenom"));
        intent.putExtra("user_numero", intent.getStringExtra("user_numero"));
        intent.putExtra("user_mail", intent.getStringExtra("user_mail"));
        intent.putExtra("user_mot_de_passe", intent.getStringExtra("user_mot_de_passe"));
        startActivity(intent);
    }

}
