package com.example.n3cproject.ui.ordonnance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.n3cproject.R;
import com.example.n3cproject.ui.meditation.MeditationViewModel;

public class OrdonnanceFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_image_remplacer_remove, container, false);
        final Button button_retour = root.findViewById(R.id.button_remplacer);
        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();
            }
        });

        return root;
    }

}