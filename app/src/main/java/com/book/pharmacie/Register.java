package com.book.pharmacie;

import android.content.Intent;
import android.os.Bundle;
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

import com.book.pharmacie.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText phoneInput;
    private EditText addressInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private LinearLayout signupButton,login;
    private ImageView back_button;
    DatabaseReference databaseReference;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
         preferencesHelper = new SharedPreferencesHelper(this);

        back_button = findViewById(R.id.back_button);
        signupButton = findViewById(R.id.signup_button);
        login = findViewById(R.id.google_signup_button);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                startActivity(new Intent(Register.this,Login.class));
                //finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(zoomAnimation);
                // Récupérer les données lorsque le bouton est cliqué
                registerUser();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(Register.this, RegisLogin.class));
                finish();
            }
        });

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        addressInput = findViewById(R.id.address_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        if (!name.isEmpty() || !phone.isEmpty() || !password.isEmpty()){

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Les mots de passe ne correspondent pas!", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseReference oneuser = databaseReference.push();
            String nouvelId = oneuser.getKey();
            User user = new User(nouvelId,name, email, phone, password);
            oneuser.setValue(user);
            preferencesHelper.addUser(nouvelId, name, email, phone, password);

            startActivity(new Intent(Register.this,MainActivity.class));
            finish();
        }else {
            Toast.makeText(this, "Veuillez verifier vos champs!!", Toast.LENGTH_SHORT).show();
        }
    }
}