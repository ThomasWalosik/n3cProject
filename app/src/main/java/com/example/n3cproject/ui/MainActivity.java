package com.example.n3cproject.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.R;
import com.example.n3cproject.ui.meditation.MeditationFragment;
import com.example.n3cproject.ui.ordonnance.OrdonnanceActivity;
import com.example.n3cproject.ui.recherche.RechercheActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity activity;
    public static final String PREFS_NAME = "SharedPrefsFile";
    public static Boolean isBackAnotherActivity = false;
    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;
    //public static String variable_globale_prenom="";
    //private static String variable_globale_age="";
    //private static String variable_globale_type_cancer="";
    //private static String variable_globale_poids="";
    public static int isOpened = 0;  // l'item Informations Personnelles fermé par défaut
    ExpandableListAdapter expandableListAdapter;
    static ExpandableListView expandableListView;
    // création de la liste de String contenant l'ensemble des items pères
    List<String> headerList = new ArrayList<>();
    // String correspondant aux items fils de l'item père Informations Personelles
    String childList;
    //final Toolbar toolbar = findViewById(R.id.toolbar);
    Button button;
    DrawerLayout drawer;
    static SharedPreferences pref;
    SharedPreferences.Editor ed;
    public static int position_age2, position_poids2, position_type2;
    //TODO remplacer variable_global_... dans ExpandableListAdapter par MainActivity.prenom, etc
    static String prenom = "";
    static String age ="";
    static String poids ="";
    static  String type_cancer = "";
    static Integer lastExpandedGroupPosition = -1;

    public MainActivity() {
        System.out.println("JE SUIS DANS LE MAIN_ACTIVITY.java, isOpened="+isOpened);
        isOpened=0;
        System.out.println("JE SUIS DANS LE MAIN_ACTIVITY.java, et maintenant isOpened="+isOpened);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*try {
            databasehelper = new DataBaseHelper(MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        pref = getSharedPreferences(PREFS_NAME, 0); //0 = MODE_PRIVATE

        //setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu_dots);
        // Masquer le texte dans la bar toolbar
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //ne pas autoriser la rotation du telephone
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // cacher le clavier au lancement
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //demande de permission a l'acces aux photos pour l'importation de l'ordonnace
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity = this;
        drawer = findViewById(R.id.drawer_layout);

        //Ancien Button Messagerie Android
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        myDialog = new Dialog(this);
        activity = this;
        Button button_search = findViewById(R.id.app_bar_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //création d'une nouvelle Activity pour la recherche
                //new RechercheActivity();
                Intent intent = new Intent(MainActivity.this, RechercheActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_left_in,R.anim.trans_left_out);
                System.out.println("je viens de cliquer sur le bouton recherche");
            }
        });

        Button button_dots_nav = findViewById(R.id.app_bar_main_nav);
        button_dots_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ouverture de nav
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        //si on revient d'une autre activity, on ouvre le menu TODO a transformer en fonction()
        if(isBackAnotherActivity){
            isBackAnotherActivity = false;
            drawer = findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.LEFT);
        }
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
                R.id.nav_home, R.id.nav_rappel) //TODO rajouter les pages de l'activity MainActivity (Maps, etc..)
                .setDrawerLayout(drawer)
                .build();

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);


        // pour reset les donnees de l'appli (infos perso) TODO a commenter
        ed = pref.edit();
        ed.putBoolean("firstrun", true);
        ed.apply();

        if (pref.getBoolean("firstrun", true)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("prenom","");
            editor.putInt("ageID",0);
            editor.putString("age","");
            editor.putInt("poidsID",0);
            editor.putString("poids","");
            editor.putInt("typeCancerID",0);
            editor.putString("type","");
            editor.putBoolean("firstrun", false);
            editor.apply();
        } else {
            isOpened=1;
        }

        // récupère les valeurs enregistrés dans les variables prenom, age, poids et type de cancer à l'ouverture de l'application
        prenom = pref.getString("prenom","");
        age = pref.getString("age","");
        poids = pref.getString("poids","");
        type_cancer = pref.getString("type","");

        expandableListView = findViewById(R.id.expandableListView);

        //String menuModel = "Info";
        //headerList.add(menuModel);

        ArrayList<String> childModelsList = new ArrayList<>();

        // ajout des items pères
        headerList.add("Info");
        headerList.add("Ordonnance");
        headerList.add("Meditation");
        headerList.add("Condition");
        headerList.add("Aide");
        headerList.add("Propos");
        headerList.add("Version");

        // ajout des items fils
        childModelsList.add("Prenom");
        childModelsList.add("Age");
        childModelsList.add("Poids");
        childModelsList.add("Votre cancer");

        // création de l'adapter avec la liste des items pères et celle des fils
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList, activity, pref);
        // remplissage de l'expandableListView avec l'adapter créé
        expandableListView.setAdapter(expandableListAdapter);

        NavigationView nav = findViewById(R.id.nav_view);
        View header = nav.getHeaderView(0);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(final ExpandableListView parent, View v, int groupPosition, long id) {
                // fermeture du clavier
                //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                //inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if(groupPosition !=0 && getIsOpened()==1){
                    parent.collapseGroup(0);
                    openCloseInformationPersonnelles();
                }

                if (groupPosition==0) { // si item Information Personnelles sélectionné
                    //openCloseInformationPersonnelles();
                    parent.expandGroup(0);
                    if (isOpened == 0) { // item fermé
                        isOpened = 1;
                        return true;
                    } else { // item ouvert
                        isOpened = 0;
                        return false;
                    }

                } else if (groupPosition==1) { // si item Ordonnance sélectionné
                    //myDialog.setContentView(R.layout.custompopup);
                    //Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //myDialog.show();

                    System.out.println("je clique sur le bouton menu ordonnance");
                    //new OrdonnanceFragment();
                    //new OrdonnanceActivity();
                    Intent intent = new Intent(MainActivity.this, OrdonnanceActivity.class);
                    //drawer.closeDrawer(GravityCompat.START);
                    startActivity(intent);
                    //animation qui fonctionne pas surement a cause de l'API <<
                    overridePendingTransition(R.anim.trans_left_in,R.anim.trans_left_out);
                    return true;

                } else if (groupPosition==2) { // si item Meditation sélectionné
                    //Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //TODO rattacher l'activity de "Bien-être"
                    new MeditationFragment();
                    //myDialog.show();
                    //ferme le menu
                    drawer.closeDrawer(GravityCompat.START);
                    return true;

                } else if (groupPosition==3) { // si item Conditions d'Utilisations sélectionné
                    myDialog.setContentView(R.layout.popup_menu_cgu);

                    button = myDialog.findViewById(R.id.button_ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myDialog.onBackPressed();
                        }
                    });
                    myDialog.show();

                    return true;

                } else if (groupPosition==4) { // si item Aide sélectionné
                    myDialog.setContentView(R.layout.popup_menu_aide);

                    button = myDialog.findViewById(R.id.button_ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myDialog.onBackPressed();
                        }
                    });
                    myDialog.show();
                    return true;

                } else if (groupPosition==5) { // si item A Propos sélectionné
                    myDialog.setContentView(R.layout.popup_menu_propos);

                    button = myDialog.findViewById(R.id.button_ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myDialog.onBackPressed();
                        }
                    });
                    myDialog.show();
                    return true;

                } else if (groupPosition==6) { // si item Version sélectionné
                    return true;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        /*RelativeLayout button_rappel = findViewById(R.id.button_rappel);
        button_rappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RappelsActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void openCloseInformationPersonnelles() {
        if (getIsOpened() == 0) { // item fermé
            isOpened = 1;
        } else { // item ouvert
            isOpened = 0;
        }
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
        /*switch (item.getItemId()) {
            case R.id.nav_home:
                new HomeFragment();
                return true;
            case R.id.nav_ordonnance:
                new OrdonnanceFragment();
                return true;
            case R.id.nav_meditation:
                new MeditationFragment();
                return true;
            case R.id.action_search:
                //création d'une nouvelle Activity pour la recherche
                new RechercheActivity();
                Intent intent = new Intent(this, RechercheActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
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

    /**
     * méthode renvoyant l'état (ouvert/fermé) de l'item Information Personnelles.
     * @return int ouvert=1 ou fermé=0.
     */
    public static int getIsOpened(){
        return isOpened;
    }
    /**
     * This method is used to set shared preferences informations personnelles
     */
    public static void setPreferencesInfoPerso() {
        SharedPreferences.Editor editor;
        editor = pref.edit();
        editor.putInt("ageID", position_age2);
        editor.putInt("poidsID", position_poids2);
        editor.putInt("typeCancerID", position_type2);
        editor.commit();
    }
    /**
     * This method is used to get shared object
     */
    public static void getPreferencesInfoPerso() {
        pref.getInt("ageID", 0);
        pref.getInt("poidsID", 0);
        pref.getInt("typeCancerID", 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}