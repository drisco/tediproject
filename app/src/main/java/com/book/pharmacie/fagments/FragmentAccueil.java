package com.book.pharmacie.fagments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.Ambulance;
import com.book.pharmacie.MainActivity;
import com.book.pharmacie.Pharmacie;
import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.TopDocteur;
import com.book.pharmacie.Traditionnnel;
import com.book.pharmacie.adapter.NewsAdapter;
import com.book.pharmacie.model.Commande;
import com.book.pharmacie.model.NewsItem;
import com.book.pharmacie.model.Notification;
import com.book.pharmacie.model.TeleConsulte;
import com.book.pharmacie.model.User;
import com.book.pharmacie.service.NotiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentAccueil extends Fragment {

    private SearchView searchView;

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    TextView user_name;
    private final String channelId = "countdown_notification_channel";
    private final  int NOTIFICATION_ID = 123;
    SharedPreferencesHelper preferencesHelper;
    LinearLayout ambulance,topdoctor,tradi,pharmacie;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final int PERMISSION_REQUEST_CODE = 140;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_animation);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);

        }
        preferencesHelper = new SharedPreferencesHelper(getContext());
        User user = preferencesHelper.getUser();
        searchView =getActivity().findViewById(R.id.searchView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        tradi =view.findViewById(R.id.tradi);
        ambulance =view.findViewById(R.id.ambulance);
        topdoctor =view.findViewById(R.id.topdoctor);
        pharmacie =view.findViewById(R.id.pharmacie);
        user_name =view.findViewById(R.id.user_name);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (user !=null){
            if (!user.getName().isEmpty()){
                user_name.setText(user.getName());

                databaseReference = firebaseDatabase.getReference("consultation").child(user.getUserId());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            TeleConsulte commande = null;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                commande = snapshot.getValue(TeleConsulte.class);
                            }

                            if (commande != null) {

                                String databaseDate = commande.getDateConsulte();
                                SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.FRENCH);


                                try {

                                    Date targetDate = inputFormat.parse(databaseDate);

                                    // Obtenir la date actuelle
                                    Date currentDate = new Date();

                                    // Calculer deux jours avant la date cible
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(targetDate);
                                    calendar.add(Calendar.DAY_OF_MONTH, -2); // Reculer de 2 jours
                                    Date twoDaysBefore = calendar.getTime();

                                    // Afficher les résultats pour débogage
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    System.out.println("Deux jours avant : " + dateFormat.format(twoDaysBefore));
                                    System.out.println("Date cible : " + dateFormat.format(targetDate));
                                    System.out.println("Date actuelle : " + dateFormat.format(currentDate));

                                    // Comparaison des dates
                                    if (!currentDate.before(twoDaysBefore) && currentDate.before(targetDate)) {
                                        System.out.println("Date currentDate Comparaison: " + targetDate);
                                        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                                        Intent intent = new Intent(getContext(), NotiService.class);
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                                getContext(),
                                                (int) System.currentTimeMillis(),
                                                intent,
                                                PendingIntent.FLAG_IMMUTABLE
                                        );

                                        if (alarmManager != null) {
                                            calendar.set(Calendar.HOUR_OF_DAY, 16);
                                            calendar.set(Calendar.MINUTE, 0);
                                            calendar.set(Calendar.SECOND, 0);

                                            long interval = 10 * 60 * 1000;

                                            alarmManager.setRepeating(
                                                    AlarmManager.RTC_WAKEUP,
                                                    calendar.getTimeInMillis(),
                                                    interval,
                                                    pendingIntent
                                            );
                                        }
                                    } else {
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Aucune commande trouvée.");
                            }
                        } else {
                            System.out.println("Aucune donnée disponible dans la base de données.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Erreur lors de la récupération des commandes.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



        newsList = new ArrayList<>();
// Ajouter des exemples de données avec des liens d'images
        newsList.add(new NewsItem("COVID-19 : Symptômes et prévention", "Les symptômes courants incluent la fièvre et la toux.", "https://www.pngitem.com/pimgs/m/103-1035945_coronavirus-covid-19-illustration.png", " 5 min read","",""));
        newsList.add(new NewsItem("Maladies cardiaques : Signes d'alerte", "Les premiers symptômes incluent des douleurs thoraciques.", "https://images.rawpixel.com/image_png_800/czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvam9iNjc5LTEwMy1wLWwxNjd4ZGdvLnBuZw.png", "3 min read","",""));
        newsList.add(new NewsItem("Gestion du diabète", "Surveillance des niveaux de sucre dans le sang.", "https://www.diabetologie-pratique.com/sites/www.diabetologie-pratique.com/files/styles/une_journal_578_383/public/images/article_journal/pancreas_178787544_1.png?itok=RoqBrqVu", " 4 min read","",""));
        newsList.add(new NewsItem("Vaccination", "La vaccination aide à prévenir la propagation des maladies.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbaY3ktU7Ap8-boOo3l_hGh9cTHFbQ5TtMCg&s", " 2 min read","",""));
        newsList.add(new NewsItem("Santé mentale : Dépression", "Les signes incluent une tristesse persistante.", "https://www.britishjournalofcommunitynursing.com/media/qlga1gyl/mental_health_02.png?width=1002&height=564&bgcolor=White&v=1da9b0438995bc0", " 6 min read","",""));
        newsList.add(new NewsItem("Alimentation saine", "Une alimentation équilibrée doit inclure des fruits et légumes.", "https://w7.pngwing.com/pngs/512/1024/png-transparent-eating-healthy-diet-food-healthy-food-natural-foods-food-weight-loss-thumbnail.png", " 5 min read","",""));
        newsList.add(new NewsItem("Cancer : Détection précoce", "La détection précoce améliore les chances de traitement.", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-stem-cell-cancer-virus-png-image_2844867.jpg", " 4 min read","",""));
        newsList.add(new NewsItem("Obésité : Risques", "L'obésité augmente le risque de maladies cardiaques.", "https://image.similarpng.com/very-thumbnail/2020/10/Fat-man-playing-sport-isolated-on-transparent-background-PNG.png", " 3 min read","",""));
        newsList.add(new NewsItem("Asthme", "Les déclencheurs incluent les allergènes et la fumée.", "https://png.pngtree.com/png-clipart/20210415/ourmid/pngtree-world-asthma-day-medical-mold-png-image_3221210.jpg", " 2 min read","",""));
        newsList.add(new NewsItem("Hypertension", "Surveillance régulière de la tension artérielle.", "https://png.pngtree.com/png-vector/20240203/ourlarge/pngtree-hypertension-3d-illustration-png-image_11591845.png", "6 min read","",""));


        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(newsAdapter);

        // Ajoutez le ScrollListener pour cacher la barre de navigation
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling down, hide the navigation bar
                    ((MainActivity) getActivity()).hideBottomNavigation();
                } else if (dy < 0) {
                    // Scrolling up, show the navigation bar
                    ((MainActivity) getActivity()).showBottomNavigation();
                }
            }
        });

        pharmacie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(getActivity(), Pharmacie.class));

            }
        });

        tradi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(getActivity(), Traditionnnel.class));

            }
        });

        topdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(getActivity(), TopDocteur.class));

            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(getActivity(), Ambulance.class));

            }
        });
        return view;

    }


}


