package com.book.pharmacie.fagments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.book.pharmacie.Login;
import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.model.User;

public class FragmentProfil extends Fragment {
    SharedPreferencesHelper preferencesHelper;
    ImageView deco;
    TextView deco1,user_name;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profil, container, false);
        Animation zoomAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_animation);
        preferencesHelper = new SharedPreferencesHelper(getContext());
        deco= view.findViewById(R.id.deco);
        deco1= view.findViewById(R.id.deco1);
        user_name= view.findViewById(R.id.user_name);
        User user =preferencesHelper.getUser();

        user_name.setText(user.getName());
        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startAnimation(zoomAnimation);
                preferencesHelper.deleteUser();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });
        deco1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startAnimation(zoomAnimation);
                preferencesHelper.deleteUser();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
