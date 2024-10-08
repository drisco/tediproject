package com.book.pharmacie;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.PharmacyAdapter;
import com.book.pharmacie.model.Pharmacy;

import java.util.Arrays;
import java.util.List;

public class Pharmacie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pharmacie);

        RecyclerView pharmacieDisponibleList = findViewById(R.id.pharmacie_disponible_list);
        RecyclerView pharmacieGardeList = findViewById(R.id.pharmacie_garde_list);

// Example data
        List<Pharmacy> availablePharmacies = Arrays.asList(
                new Pharmacy("Pharmacie Santé Cocody", R.drawable.phar),
                new Pharmacy("Pharmacie d'Abobo gare", R.drawable.phar),
                new Pharmacy("Pharmacie de Yopougnon gesco", R.drawable.phar),
                new Pharmacy("Pharmacie etoile de Vallon ", R.drawable.phar),
                new Pharmacy("Pharmacie d'Angré chu", R.drawable.phar)
        );

        List<Pharmacy> onCallPharmacies = Arrays.asList(
                new Pharmacy("Pharmacie de la Paix", R.drawable.phar),
                new Pharmacy("Pharmacie de la Riviera2", R.drawable.phar),
                new Pharmacy("Pharmacie de la Riviera3", R.drawable.phar),
                new Pharmacy("Pharmacie de la Riviera1", R.drawable.phar),
                new Pharmacy("Pharmacie de la Vallon", R.drawable.phar),
                new Pharmacy("Pharmacie de la Plateaux", R.drawable.phar)
        );

// Set up adapters
        PharmacyAdapter disponibleAdapter = new PharmacyAdapter(this, availablePharmacies);
        PharmacyAdapter gardeAdapter = new PharmacyAdapter(this, onCallPharmacies);

        pharmacieDisponibleList.setAdapter(disponibleAdapter);
        pharmacieGardeList.setAdapter(gardeAdapter);

// Optional: Set layout managers if you haven't already in XML
        pharmacieDisponibleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        pharmacieGardeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}