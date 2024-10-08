package com.book.pharmacie;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.CartAdapter;
import com.book.pharmacie.fagments.FragmentAccueil;
import com.book.pharmacie.model.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartList;
    private TextView cartTitle;
    private TextView totalTextView,cart_subtotal,cart_tax;
    private TextView checkoutButton;
    int incr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        cartTitle = findViewById(R.id.cart_title);
        checkoutButton = findViewById(R.id.validate_button);
        cart_subtotal = findViewById(R.id.cart_subtotal);
        totalTextView = findViewById(R.id.cart_total);
        cart_tax = findViewById(R.id.cart_tax);

        // Récupérer la liste des produits ajoutés au panier depuis l'intent
        ArrayList<Product> cartList = getIntent().getParcelableArrayListExtra("cart_list");

        System.out.println("DHFJSSJJJJJJJJJJJJJJJJJJJJJJJJSHSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS "+cartList.toString());
        // Configurer le RecyclerView
        cartAdapter = new CartAdapter(this, cartList);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        // Si besoin, gérez d'autres actions (ex: passer à la caisse avec le bouton "checkout")
        checkoutButton.setOnClickListener(view -> {
            view.startAnimation(zoomAnimation);
            // Logique de passage à la caisse ici
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void prixInitial(double totalPrice) {
        cart_subtotal.setText(String.format("%d CFA", (int) totalPrice));
        totalTextView.setText(String.format("%d CFA", (int) (totalPrice + 1000)));

    }

    public void updateTotalPrice(double total, double price) {
        cart_subtotal.setText(String.format("%d CFA", (int) total));
        totalTextView.setText(String.format("%d CFA", (int) (total + 1000)));
    }
    @Override
    public void onBackPressed() {
        incr++;
        if (incr==1){
            super.onBackPressed();
            startActivity(new Intent(CartActivity.this, MainActivity.class));
            finish();
        }
    }
}