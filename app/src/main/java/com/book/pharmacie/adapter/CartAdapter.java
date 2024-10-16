package com.book.pharmacie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private View cardBankLayout;
    Product product;
    private boolean isCardBankVisible = true;

    public CartAdapter(CartActivity activity, List<Product> cartItems, View cardBankLayout) {
        this.cartItems = cartItems;
        this.cartActivity = activity;
        this.quantities = new HashMap<>();
        this.cardBankLayout = cardBankLayout;

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
        // Utiliser une variable locale `product` ici
        product = cartItems.get(position);
        int quantity = quantities.get(product);

        holder.bind(product, quantity);
        prixInitial();

        holder.cv.setOnClickListener(v -> {
            // Basculer la visibilité de `cardBankLayout`
            if (isCardBankVisible) {
                cardBankLayout.setVisibility(View.GONE);
            } else {
                cardBankLayout.setVisibility(View.VISIBLE);
            }
            isCardBankVisible = !isCardBankVisible;
        });

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // Image par défaut
                .into(holder.cart_item_image);

        // Associer les boutons d'incrémentation et de décrémentation uniquement à cet item spécifique
        holder.incrementButton.setOnClickListener(v -> {
            // Toujours obtenir la position actuelle du ViewHolder
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Product currentProduct = cartItems.get(adapterPosition);
                int currentQuantity = quantities.get(currentProduct);
                currentQuantity++;
                quantities.put(currentProduct, currentQuantity);
                holder.updateQuantity(currentProduct, currentQuantity);
                updateTotalPrice();
            }
        });

        holder.decrementButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Product currentProduct = cartItems.get(adapterPosition);
                int currentQuantity = quantities.get(currentProduct);
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantities.put(currentProduct, currentQuantity);
                    holder.updateQuantity(currentProduct, currentQuantity);
                    updateTotalPrice();
                }
            }
        });

        holder.removeButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Product currentProduct = cartItems.get(adapterPosition);
                cartItems.remove(adapterPosition);
                quantities.remove(currentProduct);
                notifyItemRemoved(adapterPosition);
                updateTotalPrice();
                prixInitial();
            }
        });
    }

    private void prixInitial() {
        totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice();
        }

        cartActivity.prixInitial(totalPrice);
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice() * quantities.get(product);
        }

        cartActivity.updateTotalPrice(totalPrice, product.getPrice()); // Mettre à jour le prix total dans l'activité
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public Map<Product, Integer> getCartItemsWithQuantities() {
        return quantities;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, quantityNumber, price;
        ImageView incrementButton, decrementButton, removeButton, cart_item_image;
        LinearLayout cv;

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_item_name);
            quantityNumber = itemView.findViewById(R.id.cart_item_quantity_number);
            price = itemView.findViewById(R.id.cart_item_price);
            incrementButton = itemView.findViewById(R.id.cart_item_increase);
            decrementButton = itemView.findViewById(R.id.cart_item_decrease);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cv = itemView.findViewById(R.id.cv);
        }

        public void bind(Product product, int quantity) {
            productName.setText(product.getName());
            price.setText(String.format("%d CFA", (int) product.getPrice()));
            quantityNumber.setText(String.valueOf(quantity));
        }

        public void updateQuantity(Product product, int quantity) {
            quantityNumber.setText(String.valueOf(quantity));
            price.setText(String.format("%d CFA", (int) (product.getPrice() * quantity)));
        }
    }
}


