package com.example.n3cproject.ui.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.n3cproject.R;
import com.example.n3cproject.ui.Donnees;

import java.util.List;

public class AdapterList extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the list of countries
    private final String[] nameRappelArray;

    //to store the list of countries
    private final String[] dateRappelArray;

    public AdapterList(Activity context, String[] nameRappelArray, String[] dateRappelArray){

        super(context,R.layout.listview_row , nameRappelArray);
        this.context = context;
        this.nameRappelArray = nameRappelArray;
        this.dateRappelArray = dateRappelArray;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.textView_nameRappel);
        TextView dateTextField = rowView.findViewById(R.id.textView_dateRappel);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameRappelArray[position]);
        dateTextField.setText(dateRappelArray[position]);

        return rowView;
    };


    /*private ArrayAdapter<String> mAdapter_list_rappel;

    //ListView listView = findViewById(R.id.list_view_rappels);
    List<String> initialList = Donnees.listRappels;

    public AdapterList(@NonNull Context context, int resource) {
        super(context, resource);
    }
    //    if(Donnees.firstRun==0) {
    //    Donnees.listRappels.clear();
    //}
    //mAdapter_list_rappel = new ArrayAdapter<>(this, R.id.textView, initialList);
    // listView.setAdapter(mAdapter_list_rappel);
*/
}
