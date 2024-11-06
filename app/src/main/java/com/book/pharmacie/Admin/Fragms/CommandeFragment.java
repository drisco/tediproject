package com.book.pharmacie.Admin.Fragms;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.book.pharmacie.Admin.Adapters.OrderAdapter;
import com.book.pharmacie.Admin.Adapters.OrderWithUser;
import com.book.pharmacie.R;
import com.book.pharmacie.model.Commande;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommandeFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Commande> commandeList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commande, container, false);


        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_commandes); // Ensure this ID matches your layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderAdapter = new OrderAdapter(getContext());
        recyclerView.setAdapter(orderAdapter);

        fetchCommandes();
        return view;
    }


    private void fetchCommandes() {
        DatabaseReference commandesRef = FirebaseDatabase.getInstance().getReference("commandes");
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");

        commandesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<OrderWithUser> ordersWithUserList = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userId = userSnapshot.getKey();

                    // Récupère le nom de l'utilisateur
                    usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userDetailsSnapshot) {
                            String userName = userDetailsSnapshot.child("name").getValue(String.class);

                            // Ajoute chaque commande avec le nom de l'utilisateur
                            for (DataSnapshot orderSnapshot : userSnapshot.getChildren()) {
                                // Vérifie si chaque commande peut être convertie en Commande
                                try {
                                    Commande commande = orderSnapshot.getValue(Commande.class);
                                    if (commande != null) {
                                        ordersWithUserList.add(new OrderWithUser(commande, userName));
                                    }
                                } catch (DatabaseException e) {
                                    // Gestion de l'exception si la conversion échoue
                                    Toast.makeText(getContext(), "Erreur de format de commande pour l'utilisateur " + userId, Toast.LENGTH_SHORT).show();
                                }
                            }
                            orderAdapter.setOrderList(ordersWithUserList); // Met à jour l'adaptateur
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(getContext(), "Erreur lors de la récupération des informations utilisateur", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des commandes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}