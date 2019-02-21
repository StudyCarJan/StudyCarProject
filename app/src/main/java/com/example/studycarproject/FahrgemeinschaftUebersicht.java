package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class FahrgemeinschaftUebersicht extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ermittleNutzer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrgemeinschaft_uebersicht);
    }

    private ArrayList<Nutzer> ermittleNutzer () {
        Geocoder coder = new Geocoder(this);
        Nutzer user = Login.currentUser;
        Datenbank daten = new Datenbank(this);
        ArrayList<Nutzer> nutzer = daten.getDBList();
        ArrayList<Nutzer> möglichkeiten = new ArrayList<Nutzer>();
        int arrayIndex = 0;

        for (Nutzer partner : nutzer) {
            int distance = this.ermittleEntfernung(user, partner, coder);

            if (distance < 10000) {
                möglichkeiten.add(arrayIndex, partner);
            }
        }

        return möglichkeiten;
    }

    private int ermittleEntfernung(Nutzer eins, Nutzer zwei, Geocoder coder) {
        double longitude1 = 0, latitude1 = 0, longitude2 = 0, latitude2 = 0;
        try {
            ArrayList<Address> address1 = (ArrayList<Address>) coder.getFromLocationName(eins.getWohnort(), 1);
            ArrayList<Address> address2 = (ArrayList<Address>) coder.getFromLocationName(zwei.getWohnort(), 1);

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
