package com.book.pharmacie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.book.pharmacie.Admin.HomeAdmin;
import com.book.pharmacie.model.Livreur;
import com.book.pharmacie.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText phone_input,password_input;
    private LinearLayout connexion,inscription;
    ImageView back_button;
    private Livreur livreur;
    SharedPreferencesHelper preferencesHelper;
    private User user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("user");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        preferencesHelper = new SharedPreferencesHelper(this);
        livreur =new Livreur();
        connexion= findViewById(R.id.signup_button);
        back_button= findViewById(R.id.back_button);
        phone_input= findViewById(R.id.phone_input);
        password_input= findViewById(R.id.password_input);
        inscription= findViewById(R.id.google_signup_button);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);

        User user = preferencesHelper.getUser();
        if (user !=null){
            if (!user.getEmail().isEmpty()){
                if (user.getEmail().equals("livreur@gmail.com")){
                    startActivity(new Intent(Login.this, HomeAdmin.class));
                    finish();
                }else {
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }
            }
        }
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("QSQSQSQSQSQSQQSQSQSQQSQQSSQQSSQQSQQSQSQSQSQQQSQQQSQQSSQSSQSQSQS "+livreur.getNumero()+livreur.getMdp());
                view.startAnimation(zoomAnimation);
                if (!phone_input.getText().toString().isEmpty() || !password_input.getText().toString().isEmpty()){
                    if (phone_input.getText().toString().equals(livreur.getNumero()) && password_input.getText().toString().equals(livreur.getMdp())){
                        preferencesHelper.addUser(livreur.getId(), livreur.getNom(), livreur.getEmail(), livreur.getNumero(), livreur.getMdp());
                        Intent intent = new Intent(Login.this, HomeAdmin.class);
                        startActivity(intent);
                        finish();
                    }else{
                        checkIfPhoneExists(phone_input.getText().toString(),password_input.getText().toString());
                    }
                }else{
                    Toast.makeText(Login.this, "Veuillez remplir tout les champs!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                // Récupérer les données lorsque le bouton est cliqué
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                // Récupérer les données lorsque le bouton est cliqué
                startActivity(new Intent(Login.this, RegisLogin.class));
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void checkIfPhoneExists(String phoneNumber, String mdp) {
        Query query = usersRef.orderByChild("phoneNumber").equalTo(phoneNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Numéro trouvé, récupérer l'utilisateur comme objet User
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                         user = userSnapshot.getValue(User.class);
                        if (user.getMdp().equals(mdp)) {
                            preferencesHelper.addUser(user.getUserId(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getMdp());
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    // Numéro non trouvé
                    Toast.makeText(Login.this, "Aucun utilisateur trouvé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FirebaseData", "checkIfPhoneExists:onCancelled", databaseError.toException());
            }
        });
    }
}