package com.book.pharmacie.fagments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.adapter.CommandeAdapter;
import com.book.pharmacie.model.Commande;
import com.book.pharmacie.model.Notification;
import com.book.pharmacie.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment {
    SharedPreferencesHelper preferencesHelper;
    private RecyclerView recyclerView;
    private CommandeAdapter commandeAdapter;
    private List<Notification> commandeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commandeAdapter = new CommandeAdapter(commandeList);
        recyclerView.setAdapter(commandeAdapter);
        preferencesHelper = new SharedPreferencesHelper(getContext());

        loadCommandes();

        return view;
    }

    private void loadCommandes() {
        User user = preferencesHelper.getUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifi").child(user.getUserId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commandeList.clear(); // Effacer la liste précédente
                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Notification commande = snapshot.getValue(Notification.class);
                        commandeList.add(commande);
                    }

                    commandeAdapter.notifyDataSetChanged(); // Notifier l'adapter des changements
                }else{
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des commandes.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

