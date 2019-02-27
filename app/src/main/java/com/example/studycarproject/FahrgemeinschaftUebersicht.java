package com.example.studycarproject;

import android.location.Address;
import android.location.Geocoder;
import android.net.sip.SipSession;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class FahrgemeinschaftUebersicht extends AppCompatActivity {

    TableLayout tl;
    JSONArray jsonTest;
    String test;
    Nutzer user = Login.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Nutzer> partnerDaten = new ArrayList<Nutzer>();
        partnerDaten = ermittleNutzer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrgemeinschaft_uebersicht);

        tl = (TableLayout) findViewById(R.id.myTableLayout);

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

    private ArrayList<Nutzer> ermittleNutzer() {
        Geocoder coder = new Geocoder(this);

        Datenbank daten = new Datenbank(this);
        ArrayList<Nutzer> nutzer = daten.getDBList();

        ArrayList<Nutzer> moeglichkeiten = new ArrayList<Nutzer>();

        int arrayIndex = 0;

        for (Nutzer partner : nutzer) {
            int distance = this.googleAPITest(partner);

            if (distance < 10000 && (user.getNachname() != partner.getNachname() == (distance == 0))) {
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

    public int googleAPITest(Nutzer partner) {
        String s = null, entfernung = null;
        int dist;
        getDirections gD = (getDirections) new getDirections(partner, user).execute();
        //getDirections gD = new getDirections();
        //gD = (getDirections) gD.execute();
        try {
            s = gD.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JSONObject reader = null, routes = null, legs = null, distance = null;

        try {
            StringBuffer sb = new StringBuffer();
            reader = new JSONObject(s);

            sb.append(reader.getString("routes"));
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);
            routes = new JSONObject(sb.toString());

            sb.delete(0, sb.length()-1);
            sb.append(routes.getString("legs"));
            sb.deleteCharAt(0);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);
            legs = new JSONObject(sb.toString());

            sb.delete(0, sb.length()-1);
            sb.append(legs.getString("distance"));
            sb.deleteCharAt(0);
            distance = new JSONObject(sb.toString());

            entfernung = distance.getString("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dist = Integer.parseInt(entfernung);
        return dist;
    }
}

class getDirections extends AsyncTask<Void, Void, String> {
    Nutzer partner;
    Nutzer user;

    getDirections(Nutzer partner, Nutzer user) {
        this.partner = partner;
        this.user = user;
    }

    @Override
    protected String doInBackground(Void ... voids) {
        String str = "https://maps.googleapis.com/maps/api/directions/json?units=metric&origin=" + user.getWohnort() + "&destination=" + partner.getWohnort() + "&key=AIzaSyCUdO8oJHfj-ldy7H1XIki-wrI6x481I7Q";
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(str);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }
}
