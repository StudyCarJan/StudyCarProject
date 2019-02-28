package com.example.studycarproject;

public class Fahrgemeinschaft {

    private static int counter = 0;
    private int fahrgemeinschaftID;
    private int routenID;

    public Fahrgemeinschaft(int route) {
        this.fahrgemeinschaftID = counter;
        this.routenID = route;

        counter++;
    }

    public int getFahrgemeinschaftID() {return this.fahrgemeinschaftID; }
    public int getRoute() {return this.routenID; }

}
