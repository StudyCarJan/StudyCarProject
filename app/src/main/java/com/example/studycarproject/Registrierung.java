package com.example.studycarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registrierung extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrierungAbschicken;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrierung);

        btnRegistrierungAbschicken = findViewById(R.id.btnRegistrierungAbschicken);
        btnRegistrierungAbschicken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent registrieren = new Intent(this,Bestaetigung.class);
        startActivity(registrieren);

    }
}