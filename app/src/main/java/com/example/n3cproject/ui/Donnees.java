package com.example.n3cproject.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.n3cproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.n3cproject.ui.ExpandableListAdapter.isClick_age;
import static com.example.n3cproject.ui.ExpandableListAdapter.position_age;

public class Donnees {
    public static Integer firstRun = 0;
    public static String userName = "";
    public static Integer isOpen=0;
    public static Integer ageItemPosition=0;
    public static Integer poidsItemPosition=0;
    public static Integer typeItemPosition=0;
    public static List<String> listRappels;

    Donnees(){
        listRappels = new ArrayList<>();
        listRappels.add("Aucun rappel");


    }
}
