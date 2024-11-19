package com.book.pharmacie.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.book.pharmacie.Admin.Fragms.FragmentProfil;
import com.book.pharmacie.Admin.Fragms.FramentAcceuilAdmin;
import com.book.pharmacie.Login;
import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.fagments.FragmentAccueil;
import com.book.pharmacie.fagments.FragmentNotification;
import com.book.pharmacie.fagments.FragmentRapport;
import com.book.pharmacie.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdmin extends AppCompatActivity {
    SharedPreferencesHelper preferencesHelper;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private FragmentManager fragmentManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_admin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Changer la couleur de la status bar
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_clair));
        }
        preferencesHelper = new SharedPreferencesHelper(this);
        User user = preferencesHelper.getUser();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        viewPager = findViewById(R.id.viewPager);

        viewPager.setUserInputEnabled(false);
      /*  commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdmin.this, AdminActivity.class));
                finish();
            }
        });

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesHelper.deleteUser();
                startActivity(new Intent(HomeAdmin.this, Login.class));
                finish();
            }
        });*/

        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new FramentAcceuilAdmin();
                    case 1:
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
            if (itemId == R.id.accueil) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.parametres) {
                viewPager.setCurrentItem(1);
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