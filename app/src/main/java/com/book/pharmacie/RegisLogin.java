package com.book.pharmacie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.book.pharmacie.Admin.HomeAdmin;
import com.book.pharmacie.model.User;

public class RegisLogin extends AppCompatActivity {

    LinearLayout button_login,button_register,google_button;
    SharedPreferencesHelper preferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regis_login);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
        button_login =findViewById(R.id.button_login);
        preferencesHelper = new SharedPreferencesHelper(this);
        button_register =findViewById(R.id.button_register);
        google_button =findViewById(R.id.google_button);

        User user = preferencesHelper.getUser();
        System.out.println("QVVQQVQVVQVQQVVQQVQVVQQV "+user);
        if (user !=null){
            if (user.getName().isEmpty()){
                startActivity(new Intent(RegisLogin.this,Register.class));
                finish();
            }else {
                if (user.getEmail().equals("livreur@gmail.com")){
                    startActivity(new Intent(RegisLogin.this, HomeAdmin.class));
                    finish();
                }else {
                    startActivity(new Intent(RegisLogin.this,MainActivity.class));
                    finish();
                }
            }
        }
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(RegisLogin.this,Register.class));
                finish();

            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
                startActivity(new Intent(RegisLogin.this,Login.class));
                //finish();
            }
        });

        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(zoomAnimation);
               // startActivity(new Intent(RegisLogin.this,ChatDoctor.class));
                //finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}