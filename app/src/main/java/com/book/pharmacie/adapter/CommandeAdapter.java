package com.book.pharmacie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;
import com.book.pharmacie.model.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder> {

    private List<Notification> commandes;

    public CommandeAdapter(List<Notification> commandes) {
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
        Notification commande = commandes.get(position);
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

        public void bind(Notification commande) {
            orderIdTextView.setText(commande.getType());
            totalPriceTextView.setText("Descr: " + commande.getMessage());
            String dateNotification = commande.getDate();
            String displayTime = getDisplayTime(dateNotification,commande.getHeure());

            orderStatusTextView.setText(displayTime);
        }

        private String getDisplayTime(String dateNotification, String heure) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date notificationDate = dateFormat.parse(dateNotification);
                Date currentDate = new Date();

                // Calculer la différence en jours
                long diffInMillis = currentDate.getTime() - notificationDate.getTime();
                long daysDifference = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

                if (daysDifference == 0) {
                    return "Aujourd'hui"+" "+heure;
                } else {
                    return daysDifference + " jours";
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return dateNotification; // Si erreur, retourne la date d'origine
            }
        }
    }
}

