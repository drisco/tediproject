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

public class DetailPharmacie extends AppCompatActivity {
    RecyclerView recyclerViewProducts,recyclerViewSaleProducts;
    ImageView card,back_button;
    int incr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_pharmacie);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        card = findViewById(R.id.card);
        back_button = findViewById(R.id.back_button);
         recyclerViewProducts = findViewById(R.id.recycler_view_products);
         recyclerViewSaleProducts = findViewById(R.id.recycler_view_sale_products);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Panadol", 15.99, "https://openmoise.ci/web/image/product.template/80973/image_1024?unique=834c2aa"));
        productList.add(new Product("Bodrex Herbal", 7.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJMIV7Vv1v6CVj4GAmCmnsmenTZ5FKjLfkEA&s"));
        productList.add(new Product("Konidin", 5.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdNz8kxJdU8bEGO-fyaemDt2KKzCbuvlhGIw&s"));
        productList.add(new Product("OBH Combi", 9.99, "https://res-4.cloudinary.com/dk0z4ums3/image/upload/c_scale,h_500,w_500/v1/production/pharmacy/products/1660642028_obh_combi_rasa_menthol_100_ml"));
        productList.add(new Product("Betadine", 6.99, "https://pharmacie-alliance.toopharma.com/resize/600x600/media/finish/img/normal/49/3400930108611-betadine-scrub-sol-mous-125ml.jpg"));
        productList.add(new Product("Vicks Vaporub", 4.99, "https://m.media-amazon.com/images/I/61eUUc4TlQL._AC_UF1000,1000_QL80_.jpg"));
        productList.add(new Product("Decolgen", 3.99, "https://sixmd.com/wp-content/uploads/2021/08/Decolgen-ND.jpg"));
        productList.add(new Product("Sanmol", 2.99, "https://d3bbrrd0qs69m4.cloudfront.net/images/product/apotek_online_k24klik_20201020025034359225_sanmol-60-ml.jpg"));
        productList.add(new Product("Combantrin", 8.49, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJYVqzkvmlAb_i6DAmGhI5bZQr2a_o5eZuUA&s"));
        productList.add(new Product("Imodium", 10.99, "https://cgn-mig.farmaline.be/images/BE0/130/334/6/BE01303346-p10.jpg"));
        productList.add(new Product("Paracetamol", 5.49, "https://image.made-in-china.com/202f0j00PiUlowVWCsGh/Paracetamol-Tablet-500mg-100-Tablets-Box.webp"));
        productList.add(new Product("Antigripine", 4.49, "https://cdn.shoptrader.com/shop53097/images/productimages/big/8710537790818.webp"));
        productList.add(new Product("Strepsils", 6.99, "https://cgn-mig.farmaline.be/images/BE0/220/062/4/BE02200624-p13.jpg"));
        productList.add(new Product("Panadole", 7.99, "https://cdn.redcare-pharmacie.fr/images/F10/001/782/F10001782-p1.jpg"));
        productList.add(new Product("Calpol", 4.49, "https://m.media-amazon.com/images/I/71P1hD5s19L._AC_SL1500_.jpg"));
        productList.add(new Product("Alka-Seltzer", 9.99, "https://assets.fishersci.com/TFS-Assets/CCG/product-images/F309628~p.eps-650.jpg"));
        productList.add(new Product("Telfast", 12.49, "https://tovpet.com/cdn/shop/products/16152841938511817.png?v=1653556544"));
        productList.add(new Product("Zyrtec", 11.49, "https://images.albertsons-media.com/is/image/ABS/960141466-C1N1?$ng-ecom-pdp-desktop$&defaultImage=Not_Available"));
        productList.add(new Product("Ibuprofen", 8.99, "https://i5.walmartimages.com/seo/Equate-Ibuprofen-Mini-Softgel-Capsules-200-mg-300-Count_2a5d28ee-6e1d-45e6-b6f8-be259319b68a.f418c584e57ecbdfcbe97f05b11d6347.jpeg"));
        productList.add(new Product("Aspirin", 4.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd_K2vBtQSYpLnSMR-d__9HDEHzBiRiroebg&s"));

        ProductAdapter productAdapter = new ProductAdapter(this, productList);

        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProducts.setAdapter(productAdapter);
        recyclerViewSaleProducts.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewSaleProducts.setAdapter(productAdapter);

        card.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(DetailPharmacie.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cart_list", (ArrayList<Product>) productAdapter.getCartList());
            startActivity(intent);
        });

        back_button.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            Intent intent = new Intent(DetailPharmacie.this, Pharmacie.class);
            startActivity(intent);
            finish();
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
            startActivity(new Intent(DetailPharmacie.this,Pharmacie.class));
            finish();
        }
    }
}