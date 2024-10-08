package com.book.pharmacie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.CartActivity;
import com.book.pharmacie.R;
import com.book.pharmacie.model.Product;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> cartItems; // Liste de produits
    private Map<Product, Integer> quantities; // Map pour garder une trace des quantités
    private double totalPrice = 0.0; // Pour garder une trace du prix total
    private CartActivity cartActivity; // Référence à l'activité du panier
    private Product product;

    public CartAdapter(CartActivity activity, List<Product> cartItems) {
        this.cartItems = cartItems;
        this.cartActivity = activity; // Conserver la référence à l'activité
        this.quantities = new HashMap<>();

        // Initialisez la quantité pour chaque produit dans le panier
        for (Product product : cartItems) {
            quantities.put(product, 1); // Commencer avec une quantité de 1
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
         product = cartItems.get(position);
        holder.bind(product, quantities.get(product));
        prixInitial();

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // Image par défaut
                .into(holder.cart_item_image);
        holder.incrementButton.setOnClickListener(v -> {
            int currentQuantity = quantities.get(product);
            currentQuantity++;
            quantities.put(product, currentQuantity);
            holder.updateQuantity(product, currentQuantity);
            updateTotalPrice(); // Mettre à jour le prix total
        });

        holder.decrementButton.setOnClickListener(v -> {
            int currentQuantity = quantities.get(product);
            if (currentQuantity > 1) { // Ne pas descendre en dessous de 1
                currentQuantity--;
                quantities.put(product, currentQuantity);
                holder.updateQuantity(product, currentQuantity);
                updateTotalPrice(); // Mettre à jour le prix total
            }
        });

        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            quantities.remove(product);
            notifyItemRemoved(position);
            updateTotalPrice(); // Mettre à jour le prix total
            prixInitial();
        });
    }

    private void prixInitial() {
        totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice();
        }

        cartActivity.prixInitial(totalPrice); // Mettre à jour le prix total dans l'activité
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice() * quantities.get(product);
        }

        cartActivity.updateTotalPrice(totalPrice,product.getPrice()); // Mettre à jour le prix total dans l'activité
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, quantityNumber, price;
        ImageView incrementButton, decrementButton, removeButton,cart_item_image;

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_item_name);
            quantityNumber = itemView.findViewById(R.id.cart_item_quantity_number);
            price = itemView.findViewById(R.id.cart_item_price);
            incrementButton = itemView.findViewById(R.id.cart_item_increase);
            decrementButton = itemView.findViewById(R.id.cart_item_decrease);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
        }

        public void bind(Product product, int quantity) {
            productName.setText(product.getName());
            updateQuantity(product,quantity);
            price.setText(String.format("%d CFA", (int) (product.getPrice() * quantity)));
        }

        public void updateQuantity(Product product, int quantity) {
            quantityNumber.setText(String.valueOf(quantity));
            price.setText(String.format("%d CFA", (int) (product.getPrice() * quantity)));
        }
    }
}
