package com.book.pharmacie.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.book.pharmacie.Admin.Fragms.CommandeFragment;
import com.book.pharmacie.Admin.Fragms.ConsultationFragment;
import com.book.pharmacie.R;
import com.google.android.material.tabs.TabLayout;
import android.widget.FrameLayout;

public class AdminActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TabLayout and FrameLayout
        tabLayout = findViewById(R.id.tab_layout);
        frameLayout = findViewById(R.id.frameLayout);

        // Set up the tabs
        setupTabLayout();

        // Load the default fragment
        if (savedInstanceState == null) {
            switchFragment(new CommandeFragment());
        }
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null); // Allows you to go back to the previous fragment
        fragmentTransaction.commit();
    }
}