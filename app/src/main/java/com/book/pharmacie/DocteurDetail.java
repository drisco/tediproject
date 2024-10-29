package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.adapter.DatesAdapter;
import com.book.pharmacie.model.TeleConsulte;
import com.book.pharmacie.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocteurDetail extends AppCompatActivity implements DatesAdapter.OnDateClickListener{

    private RecyclerView recyclerViewDates;
    private DatesAdapter datesAdapter;
    private ImageView btn;

    private TextView tvDoctorName, tvSpeciality, tvRating, tvDistance,online_consultation,person_consultation;
    private CircleImageView imgDoctor;
    SharedPreferencesHelper preferencesHelper;

    private String selectedDate = null; // For storing the selected date
    private String selectedTime = null;
    TextView selectedSlot = null;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_docteur_detail);
        preferencesHelper = new SharedPreferencesHelper(DocteurDetail.this);
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        User user = preferencesHelper.getUser();
        btn = findViewById(R.id.btn);
        tvDoctorName = findViewById(R.id.doctor_name);
        tvSpeciality = findViewById(R.id.doctor_specialty);
        tvRating = findViewById(R.id.doctor_rating);
        tvDistance = findViewById(R.id.doctor_distance);
        imgDoctor = findViewById(R.id.doctor_image);
        online_consultation = findViewById(R.id.online_consultation);
        person_consultation = findViewById(R.id.in_person_consultation);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(DocteurDetail.this,TopDocteur.class));
                finish();
            }
        });

        person_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                /*startActivity(new Intent(DocteurDetail.this,TopDocteur.class));
                finish();*/
            }
        });
        TextView timeSlot1 = findViewById(R.id.time_slot_1);
        TextView timeSlot2 = findViewById(R.id.time_slot_2);
        TextView timeSlot3 = findViewById(R.id.time_slot_3);
        TextView timeSlot4 = findViewById(R.id.time_slot_4);
        TextView timeSlot5 = findViewById(R.id.time_slot_5);
        TextView timeSlot6 = findViewById(R.id.time_slot_6);
        TextView timeSlot7 = findViewById(R.id.time_slot_7);
        TextView timeSlot8 = findViewById(R.id.time_slot_8);
        TextView timeSlot9 = findViewById(R.id.time_slot_9);



        View.OnClickListener slotClickListener = new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                TextView timeSlot = (TextView) v;


                if (selectedSlot != null) {
                    selectedSlot.setBackgroundResource(R.drawable.border_background);
                }

                // Sélectionner le nouveau créneau
                timeSlot.setBackgroundResource(R.drawable.selected_background);
                selectedSlot = timeSlot;
                selectedTime = timeSlot.getText().toString();
            }
        };

        String doctorName = getIntent().getStringExtra("doctor_name");
        String doctorSpeciality = getIntent().getStringExtra("doctor_speciality");
        float doctorRating = getIntent().getFloatExtra("doctor_rating", 0);
        String doctorDistance = getIntent().getStringExtra("doctor_distance");
        int doctorImageResId = getIntent().getIntExtra("doctor_image_res_id", 0);

        online_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                Intent intent =new Intent(DocteurDetail.this, PayerConsultation.class);
                intent.putExtra("patientid",user.getUserId());
                intent.putExtra("patientName",user.getName());
                intent.putExtra("dateconsulte",selectedDate);
                intent.putExtra("heureconsulte",selectedTime);
                intent.putExtra("nondocteur",doctorName);
                intent.putExtra("docteurspecialite",doctorSpeciality);
                startActivity(intent);
            }
        });

// Associer l'écouteur à chaque créneau
        timeSlot1.setOnClickListener(slotClickListener);
        timeSlot2.setOnClickListener(slotClickListener);
        timeSlot3.setOnClickListener(slotClickListener);
        timeSlot4.setOnClickListener(slotClickListener);
        timeSlot5.setOnClickListener(slotClickListener);
        timeSlot6.setOnClickListener(slotClickListener);
        timeSlot7.setOnClickListener(slotClickListener);
        timeSlot8.setOnClickListener(slotClickListener);
        timeSlot9.setOnClickListener(slotClickListener);

        // Afficher les détails
        tvDoctorName.setText("Dr. "+doctorName);
        tvSpeciality.setText(doctorSpeciality);
        tvRating.setText(String.valueOf(doctorRating));
        tvDistance.setText(doctorDistance);
        imgDoctor.setImageResource(doctorImageResId);

        recyclerViewDates = findViewById(R.id.recyclerViewDates);
        recyclerViewDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Générer les dates dynamiques et obtenir la position actuelle
        int currentDatePosition = generateDaysOfCurrentMonth();
        recyclerViewDates.scrollToPosition(currentDatePosition); // Faire défiler vers la date actuelle

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int generateDaysOfCurrentMonth() {
        List<String> daysList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDayPosition = -1; // Position de la date actuelle

        for (int i = 1; i <= maxDay; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            String day = dayFormat.format(calendar.getTime());
            String date = dateFormat.format(calendar.getTime());
            String formattedDate = day + "\n" + date; // Format "Mon\n21"
            daysList.add(formattedDate);

            // Vérifier si c'est la date actuelle
            if (formattedDate.equals(dayFormat.format(new Date()) + "\n" + dateFormat.format(new Date()))) {
                currentDayPosition = daysList.size() - 1;
            }
        }

        // Mettre à jour votre adapter ici pour passer la liste des dates
        datesAdapter = new DatesAdapter(daysList, currentDayPosition,this);
        recyclerViewDates.setAdapter(datesAdapter);
        recyclerViewDates.setHorizontalScrollBarEnabled(false);

        return currentDayPosition;
    }

    @Override
    public void onDateClick(String date) {
        this.selectedDate = date;
    }
}