package com.example.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(() -> {
            MainActivity.this.startActivity(new Intent(MainActivity.this, com.example.commonlib.MainActivity.class));
        }, 3000);
    }

    private static String staticMethod() {
        return null;
    }
}