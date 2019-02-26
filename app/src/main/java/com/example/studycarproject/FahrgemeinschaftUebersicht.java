package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class  FahrgemeinschaftUebersicht extends AppCompatActivity {

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

        LatLng heimatOrt = new LatLng(latitude1, longitude1);
        LatLng partnerOrt = new LatLng(latitude2, longitude2);

        int distance = (int) SphericalUtil.computeDistanceBetween(heimatOrt, partnerOrt);

        return distance;
    }
}
