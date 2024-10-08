package com.book.pharmacie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.DetailPharmacie;
import com.book.pharmacie.R;
import com.book.pharmacie.model.Pharmacy;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {

    private Context context;
    private List<Pharmacy> pharmacyList;

    public PharmacyAdapter(Context context, List<Pharmacy> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_item, parent, false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.pharmacyName.setText(formatPharmacyName(pharmacy.getName()));
        holder.pharmacyImage.setImageResource(pharmacy.getImageResId());
        Animation zoomAnimation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.zoom_animation);
        holder.itemView.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(context, DetailPharmacie.class);
            intent.putExtra("pharmacy_name", pharmacy.getName());
            context.startActivity(intent);
        });
    }

    private String formatPharmacyName(String name) {
        // Vérifiez si le nom est trop long et ajoutez un saut de ligne
        if (name.length() > 15) {  // Par exemple, 15 caractères
            // Rechercher le premier espace après le 15ème caractère
            int spaceIndex = name.indexOf(" ", 15);
            if (spaceIndex != -1) {
                // Remplacer l'espace par un saut de ligne
                return name.substring(0, spaceIndex) + "\n" + name.substring(spaceIndex + 1);
            }
        }
        return name; // Renvoie le nom tel quel si pas de formatage
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder {
        ImageView pharmacyImage;
        TextView pharmacyName;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacyImage = itemView.findViewById(R.id.pharmacy_image);
            pharmacyName = itemView.findViewById(R.id.pharmacy_name);
        }
    }
}

