package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.book.pharmacie.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView backButton, productImageView;
    private TextView productName, productQuantity, productPrice, productDescription, tvQuantity;
    private RatingBar ratingBar;
    private ImageView btnDecrease, btnIncrease;
    private Button btnBuy;
    private Product product;
    private ArrayList<Product> cartList;



    private int quantity = 1;
    private double productPriceValue = 9.99;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        backButton = findViewById(R.id.back_button);
        productImageView = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productQuantity = findViewById(R.id.product_quantity);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.tvDescription);
        ratingBar = findViewById(R.id.rating_bar);
        btnDecrease = findViewById(R.id.btn_decrease);
        btnIncrease = findViewById(R.id.btn_increase);
        btnBuy = findViewById(R.id.btnBuy);
        tvQuantity = findViewById(R.id.tv_quantity);
        btnBuy = findViewById(R.id.btnBuy);

        product = getIntent().getParcelableExtra("product");

        productPriceValue = product.getPrice();

        // Configuration des valeurs reçues
        productName.setText(product.getName());
        productPrice.setText(String.format("%.2f CFA", product.getPrice()));

        // Charger l'image avec Glide
        Glide.with(this)
                .load(product.getImageUrl())
                .into(productImageView);


        cartList = getIntent().getParcelableArrayListExtra("cart_list");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            updateQuantity();
        });

        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantity();
            }
        });

        // Gérer le retour en arrière
        backButton.setOnClickListener(v -> onBackPressed());
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(ProductDetailActivity.this, R.anim.zoom_animation));

                // Ajouter le produit au panier
                cartList.add(product);
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                intent.putParcelableArrayListExtra("cart_list", cartList);
                startActivity(intent);

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateQuantity() {
        tvQuantity.setText(String.valueOf(quantity));
        double totalPrice = quantity * productPriceValue;
        productPrice.setText(String.format("%.2f CFA", totalPrice));
    }
}