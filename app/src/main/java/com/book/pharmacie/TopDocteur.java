package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.DoctorAdapter;
import com.book.pharmacie.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class TopDocteur extends AppCompatActivity {

    private RecyclerView rvDoctors;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private ImageView btn_back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_docteur);
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        rvDoctors = findViewById(R.id.rv_doctors);
        btn_back = findViewById(R.id.btn_back);
        rvDoctors.setLayoutManager(new LinearLayoutManager(this));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(TopDocteur.this,MainActivity.class));
                finish();
            }
        });
        // Initialiser la liste des docteurs
        doctorList = new ArrayList<>();
        doctorList.add(new Doctor("TEDJI", "Cardiologist", 4.7, "800m", R.drawable.ic_doctor_sample));
        doctorList.add(new Doctor("BONI", "Dentist", 4.7, "800m", R.drawable.ic_doctor_sampl));
        doctorList.add(new Doctor("AFFI", "Orthopaedic", 4.7, "800m", R.drawable.ic_doctor_samp));
        doctorList.add(new Doctor("ANGE", "Cardiologist", 4.7, "800m", R.drawable.ic_doctor_sam));
        doctorList.add(new Doctor("ASHLEY", "Cardiologist", 4.7, "800m", R.drawable.ic_doctor_sample));

        // Initialiser l'adaptateur
        doctorAdapter = new DoctorAdapter(doctorList);
        rvDoctors.setAdapter(doctorAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}