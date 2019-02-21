package com.example.studycarproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Startseite extends AppCompatActivity implements View.OnClickListener {

    Button btnFahrgemeinschaft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        btnFahrgemeinschaft = findViewById(R.id.btnFahrgemeinschaft);
        btnFahrgemeinschaft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Geocoder coder = new Geocoder(this);

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


        Intent fahrgemeinschaftFinden = new Intent(this, MapsActivity.class);
        startActivity(fahrgemeinschaftFinden);
    }
}


