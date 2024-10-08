package com.book.pharmacie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.R;
import com.book.pharmacie.model.NewsItem;
import com.bumptech.glide.Glide;


import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;

    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem currentItem = newsList.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.description.setText(currentItem.getDescription());
        holder.time.setText(currentItem.getPublicationDate());
        System.out.println("image lien "+currentItem.getImageUrl());
        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);

        final boolean[] isFavorite = {false}; // Définir l'état initial

        // Afficher l'icône correcte en fonction de l'état actuel
        holder.favoriteIcon.setImageResource(isFavorite[0] ? R.drawable.bookmarkok : R.drawable.bookmark);

        // Gérer le clic sur l'icône de favori
        holder.favoriteIcon.setOnClickListener(v -> {
            isFavorite[0] = !isFavorite[0]; // Inverser l'état du favori
            holder.favoriteIcon.setImageResource(isFavorite[0] ? R.drawable.bookmarkok : R.drawable.bookmark);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, time;
        public ImageView image;
        public ImageView favoriteIcon;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            time = itemView.findViewById(R.id.news_time);
            image = itemView.findViewById(R.id.news_image);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);

        }
    }
}

