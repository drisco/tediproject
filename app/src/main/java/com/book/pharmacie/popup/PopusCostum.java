package com.book.pharmacie.popup;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.book.pharmacie.R;

public class PopusCostum extends Dialog {

    private TextView retour;

    public PopusCostum(Activity activity){
        super(activity, R.style.CustomDialogTransparent);
        setContentView(R.layout.costumer_popup);
        retour = findViewById(R.id.retour);
    }


    public TextView getRetour() {
        return retour;
    }

    public void build(){
        show();

    }
}
