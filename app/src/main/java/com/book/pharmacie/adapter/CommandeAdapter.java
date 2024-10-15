package com.book.pharmacie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;
import com.book.pharmacie.model.Commande;

import java.util.List;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder> {

    private List<Commande> commandes;

    public CommandeAdapter(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @NonNull
    @Override
    public CommandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commande, parent, false);
        return new CommandeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeViewHolder holder, int position) {
        Commande commande = commandes.get(position);
        // Mettre à jour le holder avec les informations de la commande
        holder.bind(commande);
    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public static class CommandeViewHolder extends RecyclerView.ViewHolder {
        private TextView orderIdTextView;
        private TextView totalPriceTextView;
        private TextView orderStatusTextView;

        public CommandeViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderId);
            totalPriceTextView = itemView.findViewById(R.id.totalPrice);
            orderStatusTextView = itemView.findViewById(R.id.orderStatus);
        }

        public void bind(Commande commande) {
            orderIdTextView.setText("Identifiant: " + commande.getOrderId());
            totalPriceTextView.setText("Total: " + commande.getTotalPrice());
            orderStatusTextView.setText("Status: " + commande.getOrderStatus());
        }
    }
}

