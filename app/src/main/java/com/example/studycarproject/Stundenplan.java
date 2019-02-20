package com.example.studycarproject;

public class Stundenplan {

    private static int counter = 0;
    private int stundenplanID;
    private int vorlesungstage;

    public Stundenplan(int vorlesungstage) {
        this.stundenplanID = counter;
        this.vorlesungstage = vorlesungstage;

        counter++;
    }

    public int getStundenplanID (){ return this.stundenplanID; }
    public int getVorlesungstage() {return this.vorlesungstage; }
}
