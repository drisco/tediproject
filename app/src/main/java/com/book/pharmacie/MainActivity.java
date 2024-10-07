package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.book.pharmacie.fagments.FragmentAccueil;
import com.book.pharmacie.fagments.FragmentNotification;
import com.book.pharmacie.fagments.FragmentProfil;
import com.book.pharmacie.fagments.FragmentRapport;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private FragmentManager fragmentManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        viewPager = findViewById(R.id.viewPager);

        viewPager.setUserInputEnabled(false);
        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return 4;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new FragmentAccueil();
                    case 1:
                        return new FragmentRapport();
                    case 2:
                        return new FragmentNotification();
                    case 3:
                        return new FragmentProfil();
                    default:
                        return null;
                }
            }
        };

        viewPager.setAdapter(pagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_accueil) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.action_taches) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.action_progress) {
                viewPager.setCurrentItem(2);
                return true;
            } else if (itemId == R.id.action_parametres) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });
        viewPager.setUserInputEnabled(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}