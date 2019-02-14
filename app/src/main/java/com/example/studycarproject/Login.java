package com.example.studycarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnEinloggen;
    Button btnRegistrieren;

    EditText txtEmail;
    EditText txtPasswort;
    Datenbank db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new Datenbank(this);


        btnRegistrieren = findViewById(R.id.btnRegistrieren);
        btnEinloggen = findViewById(R.id.btnEinloggen);

        txtEmail = (EditText) findViewById(R.id.tfEmail);
        txtPasswort = (EditText) findViewById(R.id.tfPasswort);

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

                ArrayList<Nutzer> listNutzer = db.getDBList();
                for (Nutzer n : listNutzer) {
                    if (n.getEmail().equals(txtEmail.getText().toString())&& n.getPasswort().equals(txtPasswort.getText().toString())) {
                        Intent einloggen = new Intent(this, Startseite.class);
                        startActivity(einloggen);
                    } else {
                        Toast.makeText(Login.this, "Login fehlgeschlagen!", Toast.LENGTH_LONG).show();
                    }
                }
            break;
        }



    }


}
