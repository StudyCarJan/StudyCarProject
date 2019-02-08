package com.example.studycarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bestaetigung extends AppCompatActivity implements View.OnClickListener {

    Button btnAbschicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestaetigung);

        btnAbschicken = findViewById(R.id.btnAbschicken);
        btnAbschicken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent abschicken = new Intent(this, Startseite.class);
        startActivity(abschicken);
    }
}
