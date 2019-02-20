package com.example.studycarproject;

import java.util.Random;

public class Nutzer {

    private int nutzerId;
    //private static int counter = 1;

    private String nachname;
    private String vorname;
    private String email;
    private int postleitzahl;
    private String wohnort;
    private String passwort;

    private String studienort;
    private int stundenplanID;

    private int fahrgemeinschaftID;

    private String bestaetigungscode;


    //zur Erstellung
    public Nutzer(String vname, String nname, String mail, String pwort, int plz, String ort,
                  String studort) {
        vorname = vname;
        nachname = nname;
        email = mail;
        postleitzahl = plz;
        wohnort = ort;
        passwort = pwort;
        studienort = studort;

        //nutzerId = counter;
        //counter++;

        Random a = new Random();
        bestaetigungscode = erstelleCode();
    }

    //für die com.example.studycarproject.Datenbank
    public Nutzer(Integer id, String vname, String nname, String mail, String pwort, int plz, String ort, String studort, String code, String fahrgemeinschaft, String stundenplan) {
        this.nutzerId = id;
        this.vorname = vname;
        this.nachname = nname;
        this.email = mail;
        this.passwort = pwort;
        this.postleitzahl = plz;
        this.wohnort = ort;
        this.studienort = studort;
        this.bestaetigungscode = code;
        //this.fahrgemeinschaft = fahrgemeinschaft;
        //this.stundenplan = stundenplan;
    }

    private String erstelleCode() {
        Random a = new Random();
        int zahl = a.nextInt(99999);
        String s = String.valueOf(zahl);
        int digits = String.valueOf(zahl).length();

        while(s.length() < 5-digits) {
            s = "0" + s;
        }

        return s;
    }

    public int getID() { return this.nutzerId; }
    public String getVorname() { return this.vorname; }
    public String getNachname() { return this.nachname; }
    public String getEmail() { return this.email; }
    public String getPasswort() { return this.passwort; }
    public int getPLZ() { return this.postleitzahl; }
    public String getWohnort() { return this.wohnort; }
    public String getStudienort() { return this.studienort; }
    public String getCode() { return this.bestaetigungscode; }
    public int getFahrgemeinschaft() { return this.fahrgemeinschaftID; }
    public int getStundenplan() { return this.stundenplanID; }
}