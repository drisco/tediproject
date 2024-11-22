package com.book.pharmacie.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.book.pharmacie.ChatDoctor;
import com.book.pharmacie.MainActivity;
import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.model.TeleConsulte;
import com.book.pharmacie.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotiService extends BroadcastReceiver {

    private final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "consultation_channel";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    SharedPreferencesHelper preferencesHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferencesHelper = new SharedPreferencesHelper(context);
        User user = preferencesHelper.getUser();
        System.out.println("vcccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("consultation").child(user.getUserId());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TeleConsulte commande = null;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        commande = snapshot.getValue(TeleConsulte.class);
                    }

                    if (commande != null) {

                        if (commande.getIsfinish()==false){

                            // Formatter la date actuelle
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.FRENCH);
                            // Formatter l'heure actuelle
                            SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);

                            // Obtenir la date et l'heure actuelles
                            Date currentDate = new Date();

                            String currentDateString = dateFormat.format(currentDate);
                            String currentTimeString = heureFormat.format(currentDate);

                            // Afficher les valeurs pour débogage
                            System.out.println("Date actuelle : " + currentDateString);
                            System.out.println("Heure actuelle : " + currentTimeString);

                            // Notification pour le rappel
                            showNotification(
                                    "Rappel de votre consultation",
                                    "Bonjour ! Nous vous rappelons que vous avez une consultation programmée pour le " +
                                            commande.getDateConsulte() + " à " + commande.getHeureConsulte() +
                                            ". Veuillez prendre les dispositions nécessaires pour être prêt à l'heure. Pour toute question ou modification, n'hésitez pas à nous contacter. Nous sommes là pour vous accompagner.",
                                    context
                            );

                            // Comparer la date et l'heure actuelles avec celles de la consultation
                            if (currentDateString.equalsIgnoreCase(commande.getDateConsulte()) &&
                                    currentTimeString.equalsIgnoreCase(commande.getHeureConsulte())) {
                                showConsultation(
                                        "Votre consultation commence maintenant",
                                        "C'est l'heure de votre consultation ! Votre rendez-vous est prévu pour aujourd'hui à " +
                                                commande.getHeureConsulte() +
                                                ". Connectez-vous dès maintenant pour profiter de votre consultation avec nos experts. Nous vous souhaitons une excellente séance.",
                                        context
                                );
                                databaseReference.child(commande.getId()).child("isfinish").setValue(true);
                            }
                        }

                    }

                } else {
                    Toast.makeText(context, "Aucune consultation trouvée.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Erreur lors de la récupération des commandes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showConsultation(String title, String message, Context context) {

        String channelId = "consultation_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.add)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
            // Créer une intention pour ouvrir l'activité appropriée lors de la clic de la notification
            Intent intent = new Intent(context, ChatDoctor.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            // Construire la notification
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void showNotification(String title, String message, Context context) {
        createNotificationChannel(context);
            System.out.println("PAS DE NOTIFICZATIONPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.add)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Consultation Channel";
            String description = "Notifications for upcoming consultations";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
