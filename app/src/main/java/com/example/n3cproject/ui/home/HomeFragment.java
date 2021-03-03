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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.n3cproject.R;
import com.example.n3cproject.ui.MainActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.page_home, container, false);
        final RelativeLayout button_rappel = root.findViewById(R.id.relative_layout_button_rappel).findViewById(R.id.button_rappel);
        button_rappel.setOnClickListener(new View.OnClickListener() {
            @Override
            /*public void onClick(View view) {
                System.out.println("je viens de cliquer sur le bouton rappel");
                new RappelFragment();
            }*/
            public void onClick(View v) {
                System.out.println("je viens de cliquer sur le bouton rappel");
                /*Fragment fragment = new RappelFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_rappels, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                Intent intent = new Intent(getParentFragment().getContext(), RappelsActivity.class);
                startActivity(intent);

                new RappelsFragment();
            }
        });
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/
        return root;
    }
}