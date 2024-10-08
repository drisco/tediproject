package com.book.pharmacie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.ProductDetailActivity;
import com.book.pharmacie.R;
import com.book.pharmacie.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<Product> cartList = new ArrayList<>();
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Animation zoomAnimation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.zoom_animation);
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " CFA");
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);

        });

        holder.btn.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_name", product.getName());
            intent.putExtra("product_price", product.getPrice());
            intent.putExtra("product_image_url", product.getImageUrl());
            context.startActivity(intent);
        });

        holder.add_to_cart.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            cartList.add(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage,add_to_cart;
        LinearLayout btn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
            btn = itemView.findViewById(R.id.btn);
        }
    }
}

