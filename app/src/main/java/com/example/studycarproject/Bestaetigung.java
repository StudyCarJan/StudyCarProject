package com.example.studycarproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bestaetigung extends AppCompatActivity implements View.OnClickListener {

    Button btnAbschicken;
    EditText txtCode;
    Datenbank db;
    Nutzer nutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestaetigung);

        btnAbschicken = findViewById(R.id.btnAbschicken);
        btnAbschicken.setOnClickListener(this);

        txtCode = (EditText) findViewById(R.id.txtCode);

        db = new Datenbank(this);

        nutzer = Registrierung.currentNutzer;

        //Email senden
        sendEMail();
    }

    @Override
    public void onClick(View v) {

        if (nutzer.getCode().equals(txtCode.getText().toString().trim()) == false) {
            Toast.makeText(Bestaetigung.this, "Code stimmt nicht überein!", Toast.LENGTH_LONG).show();
            return;
        }
        boolean erfolgreich = db.insert(nutzer);
        if (erfolgreich == true) {
            Toast.makeText(Bestaetigung.this, "Erfolgreich abgespeichert!", Toast.LENGTH_LONG).show();
            Intent abschicken = new Intent(Bestaetigung.this, Startseite.class);
            startActivity(abschicken);
        }
        else {
            Toast.makeText(Bestaetigung.this, "Fehler beim Abspeichern!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void sendEMail() {
        SendMail sm = new SendMail(this, nutzer.getEmail(), "Bestätigungscode StudyCar",
                "Für die Registrierung bei StudyCar bitte diesen Code eingeben: " + nutzer.getCode());
        sm.execute();
    }
}
