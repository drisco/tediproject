package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.ProductAdapter;
import com.book.pharmacie.fagments.FragmentAccueil;
import com.book.pharmacie.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Traditionnnel extends AppCompatActivity {

    RecyclerView recyclerViewProducts,recyclerViewSaleProducts;
    ImageView card,back_button;
    int incr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_traditionnnel);

        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        back_button = findViewById(R.id.back_button);
        card = findViewById(R.id.card);
        recyclerViewProducts = findViewById(R.id.recycler_view_products);
        recyclerViewSaleProducts = findViewById(R.id.recycler_view_sale_products);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Paludisme", 245.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHeXK_mVpBVzkOve384H1hukL6-XXMb5k8UWCyJdQZJ6EvaYVeyB5srgUfNlU2i2h5yDc&usqp=CAU"));
        productList.add(new Product("Fièvre typhoïde", 700.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRc0xq30iYkcD99XeVbxSeH1cUvrTQ6_C2ve4b4FPlS1LcUdOLhPcf1ylInmSqIJBcG9gY&usqp=CAU"));
        productList.add(new Product("Anémie", 500.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLKcDwVt9rw76_HA1fpZE1bQ7RVDfZTeH9xuc7UgFA8E2QLgEIJYywucgnsnlP0gQTf_Q&usqp=CAU"));
        productList.add(new Product("Gastro-entérite", 900.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2gTHETX9wn7lshQduA7xFEu67GzwVngspNUrKb48_PMVbq1KMjl1ocSbPakKn7rFuy5s&usqp=CAU"));
        productList.add(new Product("Toux et rhume", 600.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfY4kxHEgr_dYUls0w9SlXSkyXq8RXryif0VTItBOHkEULwMjJi6CFrBsixRtQXzwgAtE&usqp=CAU"));
        productList.add(new Product("Diabète", 400.99, "https://i0.wp.com/a.africbio.net/wp-content/uploads/2022/07/P1420127-1-scaled.jpg?fit=2560%2C1920&ssl=1"));
        productList.add(new Product("Hypertension", 300.99, "https://4.africbio.net/wp-content/uploads/2022/02/planta-de-varicocele-2-scaled.jpg"));
        productList.add(new Product("Arthrite", 200.99, "https://d.africbio.net/wp-content/uploads/2022/01/P1620825-copie-scaled.jpg"));
        productList.add(new Product("Ulcère", 800.49, "https://d.africbio.net/wp-content/uploads/2021/09/P1620812-copie-scaled.jpg"));
        productList.add(new Product("Constipation", 1000.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBFOGmD7fbMsFS06V6lXG_Z0XE0F24t46inBFTO7VTgTQSxwByISg8YnqeBCoeKxGoGJM&usqp=CAU"));
        productList.add(new Product("Insomnie", 500.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_zo6gWU6YDjO1Dfte9M2E2BXPfK98tV3y0y1-99nK6oPlN37kxvI5xVHKYiNCgQAsxK4&usqp=CAU"));
        productList.add(new Product("Douleurs menstruelles", 400.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRcXwLhmR0NTVXurPzkOgIiGPQTW_dUNl8kJ37PClJS05PxTv6X1INUryAu-XBQhYnbwo&usqp=CAU"));
        productList.add(new Product("Asthme", 600.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzEQ9ROV6UAc6X25OUfSth-VcUOjtKQmpHExXTAIMgIUOJua2oTvcWPpT0fMV_TEcYvqQ&usqp=CAU"));
        productList.add(new Product("Maux de tête", 700.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbtJcJyONN-UqCfJM-VRN1I1A6algQVirJPFzhhF_Pu0Rqz5MSpafSzQX2iTRYLl_AJoY&usqp=CAU"));
        productList.add(new Product("Vermifuges", 400.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFw7XLNot0VVk5w99UaRIsrt0k9n0u2bZlJYfX5H_9HiGkDYtove-VMtQSEAx_-_nQEQo&usqp=CAU"));
        productList.add(new Product("Hémorroïdes", 900.99, "https://www.alytashop.com/wp-content/uploads/2023/07/bouteille-de-racines-alytashop.png"));
        productList.add(new Product("Fatigue chronique", 1200.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSs7rJTeRoVvTphYw1bhM1AKQH7ODLon-b80u5KrhjhY_xWV5T3hHmTrwNs7izvBEmWXWs&usqp=CAU"));
        productList.add(new Product("Douleurs dentaires", 1100.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyenC14s5e3GpGjD-VdNcaZ2l-Mc_Zx5t8X1d-XqqGvJBeim_L8GEk-0oUTf1LeaJ6ed8&usqp=CAU"));
        productList.add(new Product("Pied d'athlète", 800.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfASTa6232zivy59gM16vqyuunNXa8HjOQ_IVIbFnJb26IgIAGoWUwV_Est9IhQXxT2js&usqp=CAU"));
        productList.add(new Product("Sinusite", 400.99, "https://image.made-in-china.com/202f0j00LkagfiPRRzqb/Veterinary-Drug-Chinese-Traditional-Drug-Anti-Cough-Oral-Solution-for-Poultry.webp"));

        ProductAdapter productAdapter = new ProductAdapter(this, productList);

        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProducts.setAdapter(productAdapter);
        recyclerViewSaleProducts.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewSaleProducts.setAdapter(productAdapter);

        card.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(Traditionnnel.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cart_list", (ArrayList<Product>) productAdapter.getCartList());
            startActivity(intent);
        });

        back_button.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(Traditionnnel.this, MainActivity.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        incr++;
        if (incr==1){
            super.onBackPressed();
            startActivity(new Intent(Traditionnnel.this, MainActivity.class));
            finish();
        }
    }
}