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

    String vname;
    String nname;
    String email;
    String passwort1;
    String passwort2;
    String plz;
    String ort;

    public static Nutzer currentNutzer;


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
        vname = txtVname.getText().toString().trim();
        nname = txtNName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        passwort1 = txtPasswortSetzen.getText().toString().trim();
        passwort2 = txtPasswortBestaetigen.getText().toString().trim();
        plz = txtPlz.getText().toString().trim();
        ort = txtOrt.getText().toString().trim();

        boolean check = checkData();
        if (check == true) {
            //Benutzer registrieren
            currentNutzer = new Nutzer(vname, nname, email, passwort1, Integer.parseInt(plz), ort, "Mosbach");

            boolean vorhanden = db.searchforDoubles(currentNutzer);
            if (vorhanden == false) {

                Intent registrieren = new Intent(Registrierung.this, Bestaetigung.class);
                startActivity(registrieren);

            }

            else {
                Toast.makeText(Registrierung.this, "Benutzer bereits vorhanden!", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
    private boolean checkData() {
        //Alles gefüllt?
        if (vname.isEmpty() || nname.isEmpty() || email.isEmpty() || passwort1.isEmpty() || passwort2.isEmpty() || plz.isEmpty() || ort.isEmpty()) {
            Toast.makeText(Registrierung.this, "Es müssen alle Felder ausgefüllt sein!", Toast.LENGTH_LONG).show();
            return false;
        }
        //PLZ aus Zahlen?
        if (!plz.matches("[0-9]+")) {
            Toast.makeText(Registrierung.this, "Postleitzahl muss eine Zahl sein!", Toast.LENGTH_LONG).show();
            return false;
        }
        //Passwörter müssen übereinstimmen
        if (passwort1.equals(passwort2) == false) {
            Toast.makeText(Registrierung.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_LONG).show();
            return false;
        }
        //@-Zeichen in EMail?
        if (email.contains("@") == false) {
            Toast.makeText(Registrierung.this, "E-Mail muss @-Zeichen enthalten!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}