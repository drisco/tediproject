package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.book.pharmacie.model.TeleConsulte;
import com.book.pharmacie.model.User;
import com.book.pharmacie.popup.PopusCostum;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayerConsultation extends AppCompatActivity {

    private String patientId, consultationDate, consultationTime, doctorName, doctorSpeciality,patientName;
    private TextView validateButton;
    private PopusCostum popusCostum;
    private ImageView backButton;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payer_consultation);
        validateButton =findViewById(R.id.validateButton);
        backButton =findViewById(R.id.backButton);
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        Intent intent = getIntent();
        patientName = intent.getStringExtra("patientName");
        patientId = intent.getStringExtra("patientid");
        consultationDate = intent.getStringExtra("dateconsulte");
        consultationTime = intent.getStringExtra("heureconsulte");
        doctorName = intent.getStringExtra("nondocteur");
        doctorSpeciality = intent.getStringExtra("docteurspecialite");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(PayerConsultation.this,DocteurDetail.class));
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("consultation").child(patientId);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);

                DatabaseReference oneuser = databaseReference.push();
                String nouvelId = oneuser.getKey();
                TeleConsulte user = new TeleConsulte(nouvelId,doctorName, doctorSpeciality,patientName, consultationDate, consultationTime);
                oneuser.setValue(user);

                popusCostum = new PopusCostum(PayerConsultation.this);
                popusCostum.setCancelable(false);
                popusCostum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                popusCostum.show();

                popusCostum.getRetour().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popusCostum.cancel();
                        view.startAnimation(zoomAnimation);
                        startActivity(new Intent(PayerConsultation.this,MainActivity.class));
                        finish();
                    }
                });

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}