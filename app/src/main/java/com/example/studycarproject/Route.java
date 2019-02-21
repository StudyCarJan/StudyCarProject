package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.ArrayList;

public class Route {
    private static int counter;

    private int routenid;
    private String start;
    private String ende;

    public Route(String start, String ende){
        this.routenid = counter;
        this.start = start;
        this.ende = ende;

        counter++;
    }

    public int getRoutenID() {return this.routenid; }
    public String getStart() { return this.start; }
    public String getEnde() {return this.ende; }

}
