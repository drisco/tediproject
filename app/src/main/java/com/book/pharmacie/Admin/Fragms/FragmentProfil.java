package com.book.pharmacie.Admin.Fragms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.book.pharmacie.Admin.HomeAdmin;
import com.book.pharmacie.Login;
import com.book.pharmacie.R;
import com.book.pharmacie.SharedPreferencesHelper;
import com.book.pharmacie.model.User;

public class FragmentProfil extends Fragment {

    private TextView user_name;
    private Button logout_button;
    SharedPreferencesHelper preferencesHelper;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profiladmin, container, false);
        preferencesHelper = new SharedPreferencesHelper(getContext());
        User user = preferencesHelper.getUser();
        user_name= view.findViewById(R.id.user_name);
        logout_button= view.findViewById(R.id.logout_button);
        user_name.setText(user.getName());

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesHelper.deleteUser();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
