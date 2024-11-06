package com.book.pharmacie.Admin.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.book.pharmacie.R;
import com.book.pharmacie.model.TeleConsulte;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ViewHolder> {

    private List<TeleConsulte> consultations;

    public ConsultationAdapter(List<TeleConsulte> consultations) {
        this.consultations = consultations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeleConsulte consultation = consultations.get(position);
        holder.doctorNameTextView.setText(consultation.getNomDocteur());
        holder.patientNameTextView.setText(consultation.getNomPatient());
        holder.dateTextView.setText(consultation.getDateConsulte());
        holder.timeTextView.setText(consultation.getHeureConsulte());
        holder.specialityTextView.setText(consultation.getTypeconsulte());

        // Vérifiez si la consultation est passée
        String dateStr = consultation.getDateConsulte(); // suppose que la date est au format "yyyy-MM-dd"
        String timeStr = consultation.getHeureConsulte(); // suppose que l'heure est au format "HH:mm"
        Log.d("ConsultationInfo", "Spécialité : " + consultation.getTypeconsulte());
        try {
            // Parsez la date et l'heure de la consultation
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date consultationDateTime = dateTimeFormat.parse(dateStr + " " + timeStr);

            // Obtenez la date et l'heure actuelles
            Date currentDateTime = new Date();

            // Comparez les dates
            if (consultationDateTime != null && consultationDateTime.before(currentDateTime)) {
                // Si la consultation est passée, changez la couleur de fond ou ajoutez un indicateur
                holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0")); // fond rouge clair
                holder.dateTextView.setTextColor(Color.RED); // texte rouge pour signaler que c'est passé
                holder.timeTextView.setTextColor(Color.RED);
            } else {
                // Sinon, utilisez la couleur par défaut
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.dateTextView.setTextColor(Color.BLACK);
                holder.timeTextView.setTextColor(Color.BLACK);
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Affiche une erreur si le format de la date est incorrect
        }
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNameTextView;
        TextView patientNameTextView;
        TextView dateTextView;
        TextView timeTextView;
        TextView specialityTextView; // Ajoutez cette ligne si vous affichez la spécialité

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.doctorName);
            patientNameTextView = itemView.findViewById(R.id.patientName);
            dateTextView = itemView.findViewById(R.id.consultationDate);
            timeTextView = itemView.findViewById(R.id.consultationTime);
            specialityTextView = itemView.findViewById(R.id.doctorSpeciality); // Ajoutez cette ligne si vous affichez la spécialité
        }
    }
}
