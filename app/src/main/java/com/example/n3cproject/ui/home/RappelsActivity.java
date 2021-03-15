package com.example.n3cproject.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.n3cproject.R;

import java.text.SimpleDateFormat;
import java.util.*;

public class RappelsActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Dialog myDialog;
    private Activity activityParent;
    private Calendar myCalendar;
    private EditText edittext_date;
    private Boolean nomRappel_ok = false;
    private Boolean dateRappel_ok = false;
    private String nomRappel = "";

    String[] nameArray = {"Rappel 1","Rappel 2","Rappel 3","Rappel 4"};

    String[] dateArray = {
            "02/03/21",
            "13/09/21",
            "04/07/21",
            "03/10/21"
    };
    ListView listView;

    public RappelsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappels);
        activityParent = getParent();

        //Toolbar toolbar_rappel = findViewById(R.id.toolbar_rappels);
        //setSupportActionBar(toolbar_recherche);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //masquer le clavier a l'ouverture
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        DrawerLayout drawer_rappels = findViewById(R.id.drawer_layout_rappels);

        Button button_back = drawer_rappels.findViewById(R.id.bouton_back_rappels); //relative_layout_rappels
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AdapterList whatever = new AdapterList(this, nameArray, dateArray);
        listView = findViewById(R.id.list_view_rappels);
        listView.setAdapter(whatever);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = nameArray[position];
                System.out.println("message ="+message);
                String date = dateArray[position];
                System.out.println("date ="+date);
                //TODO etc
            }
        });

        myCalendar = Calendar.getInstance();
        edittext_date = findViewById(R.id.editTextDate);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        // date du rappel
        edittext_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RappelsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //en supposant la date valide TODO check comment proposer que les dates superieurs ou égales au jour J
                dateRappel_ok = true;

                //si le nom est valide
                if(nomRappel_ok){
                    //TODO rendre button ajouter clickable
                }
            }
        });
        // nom du rappel
        final EditText editText_nomRappel = findViewById(R.id.editText_nomRappel);
        editText_nomRappel.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                nomRappel = mEdit.toString();
                if(!nomRappel.equals("")){
                    nomRappel_ok = true;
                } else {
                    nomRappel_ok = false;
                }
                System.out.println("nomRappel = "+nomRappel+", nomRappel_ok = "+nomRappel_ok);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        /*editText_nomRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomRappel = (String) editText_nomRappel.getText().toString();
                System.out.println("nomRappel = "+nomRappel+"\n");
                // (1) si le nom n'est pas vide, on l'indique avec le boolean
                if(!nomRappel.isEmpty()){
                    nomRappel_ok = true;
                }
                // (2) et si la date est valide, on ajoute dans les variables globales et dans la listView
                if(nomRappel_ok && dateRappel_ok){
                    //TODO rendre button ajouter clickable
                }
            }
        });*/

        Button button_ajouter = findViewById(R.id.button_ajouter_rappels);
        button_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nomRappel_ok && dateRappel_ok){
                    //TODO ajout des variables globales dans la BDD et dans la listView

                    System.out.println("nomRappel = "+nomRappel+", nomRappel_ok = "+nomRappel_ok);
                    System.out.println("dateRappel = "+edittext_date.getText()+", dateRappel_ok = "+dateRappel_ok);
                    editText_nomRappel.setText("");
                    edittext_date.setText("Date");
                    nomRappel_ok = false;
                    dateRappel_ok = false;
                }
            }
        });

        Button button_supprimer = findViewById(R.id.button_supprimer_rappels);
        button_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        NavController navController = Navigation.findNavController(RappelsActivity.this, R.id.nav_host_fragment_recherche);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private SimpleDateFormat updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        edittext_date.setText(sdf.format(myCalendar.getTime()));
        return sdf;
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