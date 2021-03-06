package com.example.n3cproject.ui.ordonnance;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.n3cproject.R;
import android.app.Dialog;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.ui.MainActivity;
import com.example.n3cproject.ui.recherche.RechercheActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import static com.example.n3cproject.ui.MainActivity.*;
import static com.example.n3cproject.ui.MainActivity.isBackAnotherActivity;

public class OrdonnanceActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;
    private static final int SELECT_PICTURE = 1;
    private ImageView selectedImagePreview;
    private String selectedImagePath;
    private Toolbar navigationView;
    public OrdonnanceActivity(){
        System.out.println("JE SUIS DANS LE ORDONNANCE_ACTIVITY.java");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordonnance);
        Toolbar toolbar_ordonnance = findViewById(R.id.toolbar_ordonnance);
        setSupportActionBar(toolbar_ordonnance);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer_ordonnance = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle_ordonnance = new ActionBarDrawerToggle(
                this, drawer_ordonnance, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_ordonnance.addDrawerListener(toggle_ordonnance);
        toggle_ordonnance.syncState();

        Button button_back = findViewById(R.id.bouton_back_ordonnance);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //onBackPressed();
                isBackAnotherActivity = true;
                Intent intent = new Intent(OrdonnanceActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
            }
        });

        Button button_add_image = findViewById(R.id.button_add_image);
        button_add_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("je viens de cliquer sur le bouton pour ajouter une ordonnance\n");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
                new OrdonnanceFragment();
                /*Fragment someFragment = new OrdonnanceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_ordonnanceFragment, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();*/
            }
        });
        //l'ordonnance
        selectedImagePreview = findViewById(R.id.image_preview);
        //navigationView = findViewById(R.id.toolbar_ordonnance);
        //NavController navController = Navigation.findNavController(this, R.id.ordonnanceActivity);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        //NavController navController = Navigation.findNavController(this, R.id.ordonnanceActivity);
        //navController.navigate(R.id.action_confirmationFragment_to_secondActivity);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_ordonnanceFragment) //TODO rajouter les pages de l'activity MainActivity (Maps, etc..)
                .setDrawerLayout(drawer_ordonnance)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_ordonnance);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
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
        NavController navController = Navigation.findNavController(com.example.n3cproject.ui.ordonnance.OrdonnanceActivity.this, R.id.nav_host_fragment_ordonnance);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

}