package com.example.studycarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Datenbank extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studycar.db";
    private static final String TABLENAME = "studycar_data";
    private static final String COL_ID = "id";
    private static final String COL_VORNAME = "vorname";
    private static final String COL_NACHNAME = "nachname";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORT = "passwort";
    private static final String COL_PLZ = "postleitzahl";
    private static final String COL_WOHNORT = "wohnort";
    private static final String COL_STUDIENORT = "studienort";
    private static final String COL_CODE = "bestaetigungscode";
    private static final String COL_FAHRGEMEINSCHAFT = "fahrgemeinschaft";
    private static final String COL_STUNDENPLAN = "stundenplan";

    public Datenbank(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                COL_VORNAME + " TEXT, " + COL_NACHNAME + " TEXT, " + COL_EMAIL + " TEXT, " +
                                COL_PASSWORT + " TEXT, " + COL_PLZ + " INTEGER, " + COL_WOHNORT + " TEXT, " +
                                COL_STUDIENORT + " TEXT, " + COL_CODE + " TEXT, " + COL_FAHRGEMEINSCHAFT + " TEXT, " +
                                COL_STUNDENPLAN + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
    }

    public boolean insert(Nutzer nutzer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_ID, nutzer.getID());
        contentValues.put(COL_VORNAME, nutzer.getVorname());
        contentValues.put(COL_NACHNAME, nutzer.getNachname());
        contentValues.put(COL_EMAIL, nutzer.getEmail());
        contentValues.put(COL_PASSWORT, nutzer.getPasswort());
        contentValues.put(COL_PLZ, nutzer.getPLZ());
        contentValues.put(COL_WOHNORT, nutzer.getWohnort());
        contentValues.put(COL_STUDIENORT, nutzer.getStudienort());
        contentValues.put(COL_CODE, nutzer.getCode());
        contentValues.put(COL_FAHRGEMEINSCHAFT, nutzer.getFahrgemeinschaft());
        contentValues.put(COL_STUNDENPLAN, nutzer.getStundenplan());

        long result = db.insert(TABLENAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<Nutzer> getDBList() {
        ArrayList<Nutzer> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLENAME, null);
        while(data.moveToNext()) {
            Nutzer nutzer = new Nutzer(Integer.parseInt(data.getString(0)), data.getString(1), data.getString(2), data.getString(3), data.getString(4),
                    Integer.parseInt(data.getString(5)), data.getString(6), data.getString(7), data.getString(8), data.getString(9),
                    data.getString(10));
            returnList.add(nutzer);
        }
        data.close();
        return returnList;
    }

    public boolean searchforDoubles(Nutzer nutzer) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLENAME + " WHERE " + COL_VORNAME + "=? AND " + COL_NACHNAME + " =?";
        select = select.trim();
        Cursor data = db.rawQuery(select, new String[]{nutzer.getVorname(), nutzer.getNachname()});
        if (data.getCount() != 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
