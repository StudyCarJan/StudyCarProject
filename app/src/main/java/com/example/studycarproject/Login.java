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

        btnRegistrieren = findViewById(R.id.btnRegistrieren);
        btnEinloggen = findViewById(R.id.btnEinloggen);

        btnRegistrieren.setOnClickListener(this);
        btnEinloggen.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnRegistrieren:
                Intent registrieren = new Intent(this,Registrierung.class);
                startActivity(registrieren);
            break;

            case R.id.btnEinloggen:
                Intent einloggen = new Intent(this,Startseite.class);
                startActivity(einloggen);
            break;
        }



    }


}
