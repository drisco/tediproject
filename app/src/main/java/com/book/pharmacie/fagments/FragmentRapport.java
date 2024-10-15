package com.book.pharmacie.fagments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.book.pharmacie.R;
import com.book.pharmacie.model.Livreur;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentRapport extends Fragment {

    private GoogleMap mMap;
    private Livreur livreur;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate le layout pour ce fragment
        View view = inflater.inflate(R.layout.fragment_rapport, container, false);

        // Initialiser le livreur par défaut
        livreur = new Livreur();

        return view;
    }

}


