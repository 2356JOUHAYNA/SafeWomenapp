package com.example.safewo.ui.auth.profile;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.safewo.R;
import com.example.safewo.SplashActivity;
import com.example.safewo.util.PreferenceManager;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        btnLogout = view.findViewById(R.id.btnLogout);

        PreferenceManager preferenceManager = new PreferenceManager(requireContext());
        tvUsername.setText(preferenceManager.getUsername());

        btnLogout.setOnClickListener(v -> {
            preferenceManager.clearData();
            startActivity(new Intent(requireContext(), SplashActivity.class));
            requireActivity().finish();
        });

        return view;
    }
}

