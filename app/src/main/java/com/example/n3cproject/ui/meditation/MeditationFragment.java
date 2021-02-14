package com.example.n3cproject.ui.meditation;

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

public class MeditationFragment extends Fragment {

    private MeditationViewModel meditationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meditationViewModel =
                ViewModelProviders.of(this).get(MeditationViewModel.class);
        View root = inflater.inflate(R.layout.page_meditation, container, false);
        final TextView textView = root.findViewById(R.id.text_meditation);
        final Button button_retour = root.findViewById(R.id.button_retour);
        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("je viens de cliquer sur le bouton retour");
                getActivity().onBackPressed();
            }
        });
        meditationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}