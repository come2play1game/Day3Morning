package com.u2deom.day3morning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pages.BaseAct;

public class AppActivity extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
    }
}