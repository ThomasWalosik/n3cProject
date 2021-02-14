package com.example.n3cproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.ui.home.HomeFragment;
import com.example.n3cproject.ui.meditation.MeditationFragment;
import com.example.n3cproject.ui.ordonnance.OrdonnanceFragment;
import com.example.n3cproject.ui.recherche.RechercheActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu_dots);
        // Masquer le texte dans la bar toolbar
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        /* Ancien Button Messagerie Android
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        myDialog = new Dialog(this);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Button button_search = findViewById(R.id.app_bar_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //création d'une nouvelle Activity pour la recherche
                new RechercheActivity();
                Intent intent = new Intent(MainActivity.this, RechercheActivity.class);
                startActivity(intent);
                System.out.println("je viens de cliquer sur le bouton recherche");
            }
        });

        Button button_dots_nav = findViewById(R.id.app_bar_main_nav);
        button_dots_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ouverture de nav
                drawer.openDrawer(Gravity.LEFT);
                System.out.println("je viens de cliquer sur le bouton recherche");
            }
        });
/*
        View header = navigationView.getHeaderView(0);
        // action onClick du bouton info perso
        header.findViewById(R.id.buttonRetourMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.text_home:
                        new HomeFragment();
                        break;
                    case R.id.text_ordonnance:
                        new OrdonnanceFragment();
                        break;
                    case R.id.text_meditation:
                        new MeditationFragment();
                        break;
                }
            }
        });*/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ordonnance, R.id.nav_meditation)
                .setDrawerLayout(drawer)
                .build();

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        //TODO ATTENTION MAYBE ne pas faire de new a chaque fois mais declarer en haut un MeditationViewModel mVM = new MeditationViewModel(); (et pareil pour les autres)
        switch (item.getItemId()) {
            case R.id.nav_home:
                new HomeFragment();
                return true;
            case R.id.nav_ordonnance:
                new OrdonnanceFragment();
                return true;
            case R.id.nav_meditation:
                new MeditationFragment();
                return true;/*
            case R.id.action_search:
                //création d'une nouvelle Activity pour la recherche
                new RechercheActivity();
                Intent intent = new Intent(this, RechercheActivity.class);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_annuaire, container, false);
        try {
            View myContentView = inflater.inflate(R.layout.fragment_annuaire, null);
            myContentView.
            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
    }*/
}