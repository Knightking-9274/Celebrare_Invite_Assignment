package com.example.celebrareinvite;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    ImageView  imgView,imgView1, imgView2, imgView3, imgView4;
    Button btnOriginal, btnChoose;

    ImageButton btnImage;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);





    }
}



