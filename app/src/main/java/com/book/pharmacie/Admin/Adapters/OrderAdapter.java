package com.book.pharmacie.Admin.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;
import com.book.pharmacie.model.Commande;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderWithUser> ordersWithUserList = new ArrayList<>();
    private final Context context; // Ajout du contexte pour afficher les Toasts

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setOrderList(List<OrderWithUser> ordersWithUserList) {
        this.ordersWithUserList = ordersWithUserList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderWithUser orderWithUser = ordersWithUserList.get(position);
        Commande commande = orderWithUser.getCommande();

        holder.orderIdTextView.setText(commande.getOrderId());
        holder.userIdTextView.setText(orderWithUser.getUserName()); // Affiche le nom sans modifier Commande
        holder.totalPriceTextView.setText(commande.getTotalPrice());

        // Changez la couleur du texte en fonction de l'état de la commande
        if (commande.getOrderStatus().equals("Acceptée")) {
            holder.orderStatusTextView.setTextColor(Color.GREEN); // Changez cette couleur selon vos préférences
        } else if (commande.getOrderStatus().equals("Annulée")) {
            holder.orderStatusTextView.setTextColor(Color.RED); // Exemple pour une commande annulée
        } else {
            holder.orderStatusTextView.setTextColor(Color.BLACK); // Couleur par défaut
        }

        holder.orderStatusTextView.setText(commande.getOrderStatus());
        holder.orderDateTextView.setText(commande.getOrderDate());

        // Disable buttons if the order status is already "Acceptée" or "Annulée"
        if (commande.getOrderStatus().equals("Acceptée") || commande.getOrderStatus().equals("Annulée")) {
            holder.btnAccept.setEnabled(false);
            holder.btnAccept.setBackgroundColor(Color.GRAY);
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnCancel.setEnabled(false);
        } else {
            holder.btnAccept.setEnabled(true);
            holder.btnCancel.setEnabled(true);
        }

        holder.btnAccept.setOnClickListener(v -> {
            // Code pour changer l'état de la commande à "Acceptée"
            updateOrderStatus(commande.getOrderId(), commande.getUserId(), "Acceptée");
        });

        holder.btnCancel.setOnClickListener(v -> {
            // Code pour changer l'état de la commande à "Annulée"
            updateOrderStatus(commande.getOrderId(), commande.getUserId(), "Annulée");
        });
    }

    private void updateOrderStatus(String orderId, String userId, String newStatus) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("commandes").child(userId).child(orderId);
        orderRef.child("orderStatus").setValue(newStatus)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "État mis à jour: " + newStatus, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ordersWithUserList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, userIdTextView, totalPriceTextView, orderStatusTextView, orderDateTextView;
        Button btnAccept, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            userIdTextView = itemView.findViewById(R.id.user_id);
            totalPriceTextView = itemView.findViewById(R.id.total_price);
            orderStatusTextView = itemView.findViewById(R.id.order_status);
            orderDateTextView = itemView.findViewById(R.id.order_date);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }
    }
}