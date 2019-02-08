package com.example.studycarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnEinloggen;
    Button btnRegistrieren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistrieren = (Button) findViewById(R.id.btnRegistrieren);
        btnRegistrieren.setOnClickListener(this);

        btnEinloggen = (Button) findViewById(R.id.btnEinloggen);
        btnEinloggen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent registrieren = new Intent(this,Registrierung.class);
        startActivity(registrieren);
    }


}
