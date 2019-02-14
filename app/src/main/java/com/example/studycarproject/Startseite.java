package com.example.studycarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startseite extends AppCompatActivity implements View.OnClickListener {

    Button btnFahrgemeinschaft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        btnFahrgemeinschaft = findViewById(R.id.btnFahrgemeinschaft);
        btnFahrgemeinschaft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent fahrgemeinschaftFinden = new Intent(this, FahrgemeinschaftUebersicht.class);
        startActivity(fahrgemeinschaftFinden);
    }
}
