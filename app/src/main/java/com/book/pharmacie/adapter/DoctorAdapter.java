package com.book.pharmacie.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.DocteurDetail;
import com.book.pharmacie.R;
import com.book.pharmacie.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.tvDoctorName.setText("Dr."+doctor.getName());
        holder.tvSpeciality.setText(doctor.getSpeciality());
        holder.tvRating.setText(String.valueOf(doctor.getRating()));
        holder.tvDistance.setText(doctor.getDistance() + " away");
        // Si tu as des images spécifiques pour chaque docteur, tu peux les charger ici
        holder.imgDoctor.setImageResource(doctor.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            // Créer une intent pour la nouvelle activité
            Intent intent = new Intent(v.getContext(), DocteurDetail.class);

            // Passer les détails du docteur
            intent.putExtra("doctor_name", doctor.getName());
            intent.putExtra("doctor_speciality", doctor.getSpeciality());
            intent.putExtra("doctor_rating", doctor.getRating());
            intent.putExtra("doctor_distance", doctor.getDistance());
            intent.putExtra("doctor_image_res_id", doctor.getImageResId());

            // Démarrer l'activité
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView tvDoctorName, tvSpeciality, tvRating, tvDistance;
        ImageView imgDoctor;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tv_doctor_name);
            tvSpeciality = itemView.findViewById(R.id.tv_speciality);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            imgDoctor = itemView.findViewById(R.id.img_doctor);
        }
    }
}

