package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FahrgemeinschaftUebersicht extends AppCompatActivity {

    TableLayout tl;
    JSONArray jsonTest;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        googleAPITest();
        ArrayList<Nutzer> partnerDaten = new ArrayList<Nutzer>();
        partnerDaten = ermittleNutzer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrgemeinschaft_uebersicht);

        tl = (TableLayout)findViewById(R.id.myTableLayout);

        for (Nutzer partner : partnerDaten) {
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv1 = new TextView(this);
            String s1 = partner.getNachname() + ", " + partner.getVorname() + "\nWohnort: " + partner.getWohnort();
            tv1.setText(s1);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            tr1.addView(tv1);

            TableRow tr2 = new TableRow(this);
            tr2.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            Button b1 = new Button(this);
            b1.setText("Karte");

            tr2.addView(b1);

            TableRow tr3 = new TableRow(this);
            tr3.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            Button b2 = new Button(this);
            b2.setText("E-Mail");

            tr3.addView(b2);

            TableRow tr4 = new TableRow(this);
            tr4.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv2 = new TextView(this);
            tv2.setText("_________________________________________________________________\n");

            tr4.addView(tv2);

            tl.addView(tr1);
            tl.addView(tr2);
            tl.addView(tr3);
            tl.addView(tr4);
        }
    }

    private ArrayList<Nutzer> ermittleNutzer () {
        Geocoder coder = new Geocoder(this);
        Nutzer user = Login.currentUser;

        Datenbank daten = new Datenbank(this);
        ArrayList<Nutzer> nutzer = daten.getDBList();

        ArrayList<Nutzer> moeglichkeiten = new ArrayList<Nutzer>();

        int arrayIndex = 0;

        for (Nutzer partner : nutzer) {
            int distance = this.ermittleEntfernung(user, partner, coder);

            if (distance < 10000 && (user.getNachname()!=partner.getNachname() == (distance==0))) {
                moeglichkeiten.add(arrayIndex, partner);
                arrayIndex++;
            }
        }

        return moeglichkeiten;
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

    public void googleAPITest() {
    }
}
