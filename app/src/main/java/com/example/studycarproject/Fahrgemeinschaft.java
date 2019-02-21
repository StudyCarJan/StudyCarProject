package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.ArrayList;

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

    private int ermittleEntfernung(Nutzer eins, Nutzer zwei, Geocoder coder) {
        double longitude1 = 0, latitude1 = 0, longitude2 = 0, latitude2 = 0;
        try {
            ArrayList<Address> address1 = (ArrayList<Address>) coder.getFromLocationName("Grünsfeld", 1);
            ArrayList<Address> address2 = (ArrayList<Address>) coder.getFromLocationName("Tauberbischofsheim", 1);

            for (Address add : address1) {
                longitude1 = add.getLongitude();
                latitude1 = add.getLatitude();
            }

            for (Address add : address2) {
                longitude2 = add.getLongitude();
                latitude2 = add.getLatitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Location loc1 = new Location("");
        loc1.setLatitude(latitude1);
        loc1.setLongitude(longitude1);

        Location loc2 = new Location("");
        loc2.setLatitude(latitude2);
        loc2.setLongitude(longitude2);

        int distance = (int) loc1.distanceTo(loc2);

        return distance;
    }

}
