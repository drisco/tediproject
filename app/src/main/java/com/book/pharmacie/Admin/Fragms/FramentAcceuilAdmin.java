package com.book.pharmacie.Admin.Fragms;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.book.pharmacie.R;
import com.google.android.material.tabs.TabLayout;

public class FramentAcceuilAdmin extends Fragment {

    private FrameLayout frameLayout;
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceuiladmin, container, false);
        // Initialize TabLayout and FrameLayout
        tabLayout = view.findViewById(R.id.tab_layout);
        frameLayout = view.findViewById(R.id.frameLayout);

        // Set up the tabs
        setupTabLayout();

        // Load the default fragment
        if (savedInstanceState == null) {
            switchFragment(new CommandeFragment());
        }

        return view;
    }

    private void setupTabLayout() {
        // Add tabs for Commande and Consultation
        tabLayout.addTab(tabLayout.newTab().setText("Commandes"));
        tabLayout.addTab(tabLayout.newTab().setText("Consultations"));

        // Set a listener for tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition() == 0 ? new CommandeFragment() : new ConsultationFragment());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Handle tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle tab reselected
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null); // Allows you to go back to the previous fragment
        fragmentTransaction.commit();
    }
}
