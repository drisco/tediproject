package com.book.pharmacie.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DateViewHolder> {
    private List<String> dateList;
    private int selectedPosition = -1; // Garder la position sélectionnée
    private String currentDate; // Variable pour stocker la date actuelle
    private  OnDateClickListener onDateClickListener;



    public interface OnDateClickListener {
        void onDateClick(String date);
    }
    public DatesAdapter(List<String> dateList, int initialSelectedPosition,OnDateClickListener onDateClickListener) {
        this.dateList = dateList;
        this.selectedPosition = initialSelectedPosition;
        this.onDateClickListener = onDateClickListener;
        // Obtenir la date actuelle au format "EEE\ndd"
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String day = dayFormat.format(calendar.getTime());
        String date = dateFormat.format(calendar.getTime());
        currentDate = day + "\n" + date; // Combinaison des formats
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String date = dateList.get(position);
        holder.dateTextView.setText(date);

        // Vérifier si c'est la date actuelle
        /*if (date.equals(currentDate)) {
            holder.itemView.setBackgroundResource(R.drawable.selected_background); // Fond vert pour la date actuelle
            holder.dateTextView.setTextColor(Color.WHITE);
        } else*/
         if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.selected_background); // Fond vert pour la date sélectionnée
            holder.dateTextView.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.border_background); // État par défaut
            holder.dateTextView.setTextColor(Color.BLACK);
        }

        // Gérer le clic
        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition != position) {
                selectedPosition = position; // Mettre à jour la position sélectionnée
                notifyDataSetChanged(); // Rafraîchir la liste pour mettre à jour les fonds

                if (onDateClickListener != null) {
                    onDateClickListener.onDateClick(date);
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
        }
    }
}



