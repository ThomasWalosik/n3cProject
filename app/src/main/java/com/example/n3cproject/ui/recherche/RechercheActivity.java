package com.example.n3cproject.ui.recherche;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.R;
import com.example.n3cproject.ui.MainActivity;

import static com.example.n3cproject.R.id.drawer_layout;
import static com.example.n3cproject.ui.MainActivity.isBackAnotherActivity;

public class RechercheActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;
    private Activity activityParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        activityParent = getParent();

        //Toolbar toolbar_recherche = findViewById(R.id.toolbar_recherche);
        //setSupportActionBar(toolbar_recherche);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer_recherche = findViewById(R.id.drawerlayout_recherche);
        //ActionBarDrawerToggle toggle_recherche = new ActionBarDrawerToggle(
        //        this, drawer_recherche, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer_recherche.addDrawerListener(toggle_recherche);
        //toggle_recherche.syncState();

        Button button_back = findViewById(R.id.bouton_back_recherche);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //onBackPressed();
                Intent intent = new Intent(RechercheActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //a réactiver que si connection du menu recherche.xml avec la toolbar de la page_recherche
        //getMenuInflater().inflate(R.menu.recherche, menu);
        //System.out.println("je passe dans onCreateOptionsMenu()\n");
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(RechercheActivity.this, R.id.nav_host_fragment_recherche);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        //TODO ATTENTION MAYBE ne pas faire de new a chaque fois mais declarer en haut un MeditationViewModel mVM = new MeditationViewModel(); (et pareil pour les autres)
        switch (item.getItemId()) {
            //a réactiver que si connection du menu recherche.xml avec la toolbar de la page_recherche
            /*case R.id.action_back:
                onBackPressed();
                System.out.println("oui oui oui oui\n");
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}