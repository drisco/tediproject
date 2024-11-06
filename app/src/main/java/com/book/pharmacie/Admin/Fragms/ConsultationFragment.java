package com.book.pharmacie.Admin.Fragms;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.Admin.Adapters.ConsultationAdapter;
import com.book.pharmacie.R;
import com.book.pharmacie.model.TeleConsulte;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConsultationAdapter adapter;
    private List<TeleConsulte> consultationList;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_consultation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        consultationList = new ArrayList<>();
        adapter = new ConsultationAdapter(consultationList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("consultation");
        fetchConsultations();

        return view;
    }


    private void fetchConsultations() {
        // Écouteur pour récupérer toutes les consultations
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear(); // Vider la liste avant d'ajouter de nouvelles données

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey(); // Récupérer l'ID de l'utilisateur
                    Log.d("ConsultationFetch", "Utilisateur ID : " + userId); // Log ID utilisateur

                    for (DataSnapshot consultationSnapshot : userSnapshot.getChildren()) {
                        TeleConsulte consultation = consultationSnapshot.getValue(TeleConsulte.class);
                        if (consultation != null) {
                            consultationList.add(consultation);

                            // Log des informations de la consultation
                            Log.d("ConsultationFetch", "Consultation ID : " + consultationSnapshot.getKey());
                            Log.d("ConsultationFetch", "Nom du Docteur : " + consultation.getNomDocteur());
                            Log.d("ConsultationFetch", "Nom du Patient : " + consultation.getNomPatient());
                            Log.d("ConsultationFetch", "Date de Consultation : " + consultation.getDateConsulte());
                            Log.d("ConsultationFetch", "Heure de Consultation : " + consultation.getHeureConsulte());
                            Log.d("ConsultationFetch", "Spécialité : " + consultation.getTypeconsulte());
                        }
                    }
                }

                adapter.notifyDataSetChanged(); // Notifier l'adaptateur pour rafraîchir la liste
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs
                Log.e("ConsultationFetch", "Erreur de récupération des consultations", databaseError.toException());
            }
        });
    }}