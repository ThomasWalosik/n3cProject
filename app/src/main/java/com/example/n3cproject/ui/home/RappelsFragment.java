package com.example.n3cproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.n3cproject.R;

public class RappelsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_rappels, container, false);
        System.out.println("rappelFragment\n");
        Intent intent = new Intent(getContext(), RappelsActivity.class);
        startActivity(intent);

        //final TextView textView = root.findViewById(R.id.text_home);
        /*final RelativeLayout button_rappel = root.findViewById(R.id.id_rappel_page_principale);
        button_rappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("je viens de cliquer sur le bouton rappel");
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}