package com.example.n3cproject.ui.ordonnance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.n3cproject.R;

public class OrdonnanceFragment extends Fragment {

    private OrdonnanceViewModel ordonnanceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordonnanceViewModel =
                ViewModelProviders.of(this).get(OrdonnanceViewModel.class);
        View root = inflater.inflate(R.layout.page_ordonnance, container, false);
        final TextView textView = root.findViewById(R.id.text_ordonnance);
        ordonnanceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}