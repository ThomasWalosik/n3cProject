package com.example.n3cproject.ui.recherche;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.R;

import static com.example.n3cproject.R.id.nav_host_fragment;
import static com.example.n3cproject.R.id.nav_host_fragment_container;

public class RechercheActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Toolbar toolbar_recherche = findViewById(R.id.toolbar_recherche);
        //setSupportActionBar(toolbar_recherche);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer_recherche = findViewById(R.id.drawer_layout_recherche);
        ActionBarDrawerToggle toggle_recherche = new ActionBarDrawerToggle(
                this, drawer_recherche, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_recherche.addDrawerListener(toggle_recherche);
        toggle_recherche.syncState();

        Button next = (Button) findViewById(R.id.bouton_test_recherche);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("je viens de cliquer sur le bouton retour\n");
                onBackPressed();
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