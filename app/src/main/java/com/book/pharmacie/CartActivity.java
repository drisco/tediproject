package com.book.pharmacie;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.CartAdapter;
import com.book.pharmacie.model.Commande;
import com.book.pharmacie.model.Notification;
import com.book.pharmacie.model.Product;
import com.book.pharmacie.model.ProduitQuantite;
import com.book.pharmacie.model.User;
import com.book.pharmacie.popup.PopusCostum;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView, cart_subtotal, cart_tax, total1;
    private TextView checkoutButton;
    private LinearLayout cardbank;
    private PopusCostum popusCostum;
    String latitude,longitude;
    private int incr;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1;
    SharedPreferencesHelper preferencesHelper;
    FusedLocationProviderClient fusedLocationProviderClient;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        preferencesHelper = new SharedPreferencesHelper(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
         user = preferencesHelper.getUser();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Demander les autorisations
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Vérifier si la position n'est pas nulle
                if (location != null) {
                    // Récupérer la latitude et la longitude
                    double latitudeStr = location.getLatitude();
                    double longitudeStr = location.getLongitude();

                    // Convertir la latitude et la longitude en chaînes de caractères
                     latitude = String.valueOf(latitudeStr);
                     longitude = String.valueOf(longitudeStr);

                    // Afficher ou utiliser les valeurs en String
                    Log.d("Position", "Latitude: " + latitudeStr + ", Longitude: " + longitudeStr);

                } else {
                    Log.d("Position", "La position est null");
                }
            }
        });


        // Initialisation des vues
        total1 = findViewById(R.id.total);
        cardbank = findViewById(R.id.cardbank);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        checkoutButton = findViewById(R.id.validate_button);
        cart_subtotal = findViewById(R.id.cart_subtotal);
        totalTextView = findViewById(R.id.cart_total);
        cart_tax = findViewById(R.id.cart_tax);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commandes").child(user.getUserId());
        databaseReference1 = firebaseDatabase.getReference("notifi").child(user.getUserId());
        // Récupérer la liste des produits ajoutés au panier depuis l'intent
        ArrayList<Product> cartList = getIntent().getParcelableArrayListExtra("cart_list");

        popusCostum = new PopusCostum(this);
        popusCostum.setCancelable(false);
        popusCostum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Configurer le RecyclerView
        cartAdapter = new CartAdapter(this, cartList, cardbank);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        // Action lors du clic sur le bouton de validation (checkout)
        checkoutButton.setOnClickListener(view -> {
            view.startAnimation(zoomAnimation);
            popusCostum.show();

            popusCostum.getRetour().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popusCostum.cancel();
                    addCommande();
                }
            });
        });

        cartRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideBottomNavigation();
                } else if (dy < 0) {
                    showBottomNavigation();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addCommande() {


        // Créer une nouvelle commande
        String orderId = databaseReference.push().getKey();
        String userId = user.getUserId();
        String totalPrice = cart_subtotal.getText().toString();
        String orderStatus = "en cours";
        String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); // Date actuelle
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        ArrayList<ProduitQuantite> produit = new ArrayList<>();
        Map<Product, Integer> produits = cartAdapter.getCartItemsWithQuantities();
        for (Map.Entry<Product, Integer> entry : produits.entrySet()) {
            Product produit1 = entry.getKey();
            Integer quantite = entry.getValue();
            produit.add(new ProduitQuantite(produit1, quantite));
        }
        Commande commande = new Commande(orderId, userId, produit, totalPrice, orderStatus, orderDate,latitude,longitude);
        Notification notifi =new Notification(userId,"commande","Votre commande à ete prise en compte avec succès",orderDate,currentTime);
        databaseReference.child(orderId).setValue(commande)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        databaseReference1.child(orderId).setValue(notifi)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(CartActivity.this, "Commande validée !", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CartActivity.this, MainActivity.class)); // Redirection
                                        finish();
                                    } else {
                                        Toast.makeText(CartActivity.this, "Erreur lors de la validation de la commande", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(CartActivity.this, "Erreur lors de la validation de la commande", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void showBottomNavigation() {
        cardbank.setVisibility(View.VISIBLE);
    }

    private void hideBottomNavigation() {
        cardbank.setVisibility(View.GONE);
    }

    public void prixInitial(double totalPrice) {
        cart_subtotal.setText(String.format("%d CFA", (int) totalPrice));
        totalTextView.setText(String.format("%d CFA", (int) (totalPrice + 1000)));
        total1.setText(String.format("%d CFA", (int) (totalPrice + 1000)));
    }

    public void updateTotalPrice(double total, double price) {
        cart_subtotal.setText(String.format("%d CFA", (int) total));
        totalTextView.setText(String.format("%d CFA", (int) (total + 1000)));
        total1.setText(String.format("%d CFA", (int) (total + 1000)));
    }

    @Override
    public void onBackPressed() {
        incr++;
        if (incr == 1) {
            super.onBackPressed();
            startActivity(new Intent(CartActivity.this, MainActivity.class));
            finish();
        }
    }
}
