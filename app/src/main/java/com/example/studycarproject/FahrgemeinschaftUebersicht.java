package com.example.studycarproject;

import android.app.ProgressDialog;
import android.content.Context;
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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FahrgemeinschaftUebersicht extends AppCompatActivity {

    TableLayout tl;
    Nutzer user = Login.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Ermittle in Frage kommende Nutzer für eine Fahrgemeinschaft:
        ArrayList<Nutzer> partnerDaten = new ArrayList<Nutzer>();
        partnerDaten = ermittleNutzer();

        //Hole Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrgemeinschaft_uebersicht);

        //Baue Ausgabe dynamisch auf:
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
        //Hole alle Nutzer aus der Datenbank:
        Datenbank daten = new Datenbank(this);
        ArrayList<Nutzer> nutzer = daten.getDBList();

        //Füge alle passenden Nutzer in ein Array:
        ArrayList<Nutzer> moeglichkeiten = new ArrayList<Nutzer>();
        int arrayIndex = 0;
        for (Nutzer partner : nutzer) {
            //Alle Nutzer, die in dem gewünschten Umkreis liegen werden herausgefilter:
            int distance = this.ermittleDistanz(partner);
            if ((distance < 10000) && (user.getID() != partner.getID())) {
                moeglichkeiten.add(arrayIndex, partner);
                arrayIndex++;
            }
        }
        return moeglichkeiten;
    }

    public int ermittleDistanz(Nutzer partner) {
        String s = null, entfernung = null; int dist;

        //Erstelle Asynchronen Task für Webservice-Aufruf
        getDirections gD =  new getDirections(this,partner, user);
        gD = (getDirections) gD.execute();
        try {
         //Speichere zurückgegebenes JSON-Objekt in String ab
            s = gD.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JSONObject reader = null, routes = null, legs = null, distance = null;

        //Verarbeitung des JSON-Objektes um Distanz-Wert zu ermitteln
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
    private Nutzer user;
    private Nutzer partner;
    private Context context;
    private ProgressDialog progressDialog;

    getDirections(Context context, Nutzer partner, Nutzer user) {
        this.context = context;
        this.partner = partner;
        this.user = user;
    }

    @Override
    protected String doInBackground(Void ... voids) {
        //Lösche Leerzeichen aus den Wohnorten
        String origin = user.getWohnort();
        String destination = partner.getWohnort();
        origin.replace(" ", "");
        destination.replace(" ", "");

        //Webservice-Aufruf mit Rückgabe eines JSON-Objektes
        String str = "https://maps.googleapis.com/maps/api/directions/json?units=metric&origin=" + origin + "&destination=" + destination + "&key=AIzaSyCUdO8oJHfj-ldy7H1XIki-wrI6x481I7Q";
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
