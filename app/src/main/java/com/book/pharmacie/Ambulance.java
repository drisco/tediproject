package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.book.pharmacie.model.Livreur;

public class Ambulance extends AppCompatActivity {

    private TextView tvTitle, tvSubtitle,  btnConfirmPosition, btnCall;
    private ImageView imgAmbulance;
    //private CardView cvMedical, cvAccident, cvFire, cvOther;
    private LinearLayout b4,b3,b2,b1;
    private Livreur livreur;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ambulance);
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        // Lier les vues à leurs ID dans le fichier XML
        b4 = findViewById(R.id.b4);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);
        imgAmbulance = findViewById(R.id.img_ambulance);
        livreur= new Livreur();

        /*cvMedical = findViewById(R.id.cv_medical);
        cvAccident = findViewById(R.id.cv_accident);
        cvFire = findViewById(R.id.cv_fire);
        cvOther = findViewById(R.id.cv_other);*/

        btnConfirmPosition = findViewById(R.id.btn_confirm_position);
        btnCall = findViewById(R.id.btn_call);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
            }
        });

        btnConfirmPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                confirmPositionOnline();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                // Action pour appeler le numéro d'urgence
                callEmergency();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void callEmergency() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + livreur.getNumero()));
        startActivity(intent);
    }

    private void confirmPositionOnline() {
    }
}