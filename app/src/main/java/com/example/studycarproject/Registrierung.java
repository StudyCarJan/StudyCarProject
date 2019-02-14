package com.example.studycarproject;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Registrierung extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrierungAbschicken;
    EditText txtPasswortBestaetigen;
    EditText txtPlz;
    EditText txtNName;
    EditText txtVname;
    EditText txtEmail;
    EditText txtOrt;
    EditText txtPasswortSetzen;
    Datenbank db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrierung);

        txtPasswortBestaetigen = (EditText) findViewById(R.id.txtPasswortBestaetigen);
        txtPlz = (EditText) findViewById(R.id.txtPlz);
        txtNName = (EditText) findViewById(R.id.txtName);
        txtVname = (EditText) findViewById(R.id.txtVname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtOrt = (EditText) findViewById(R.id.txtOrt);
        txtPasswortSetzen = (EditText) findViewById(R.id.txtPasswortSetzen);

        db = new Datenbank(this);

        btnRegistrierungAbschicken = findViewById(R.id.btnRegistrierungAbschicken);
        btnRegistrierungAbschicken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Benutzer registrieren
        Nutzer nutzer = new Nutzer(txtVname.getText().toString(), txtNName.getText().toString(), txtEmail.getText().toString(),
                        txtPasswortSetzen.getText().toString(), Integer.parseInt(txtPlz.getText().toString()),
                        txtOrt.getText().toString(), null);
        boolean erfolgreich = db.insert(nutzer);
        if (erfolgreich == true) {
            Toast.makeText(Registrierung.this, "Erfolgreich abgespeichert!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(Registrierung.this, "Fehler beim Abspeichern!", Toast.LENGTH_LONG).show();
        }
        Intent registrieren = new Intent(this,Bestaetigung.class);
        startActivity(registrieren);

    }
}