package com.example.studycarproject;

public class Tag {

    private static int counter;

    private int tagID;
    private String datum;
    private String anfang;
    private String ende;

    public Tag(String datum, String anfang, String ende) {
        this.tagID = counter;
        this.datum = datum;
        this.anfang = anfang;
        this.ende = ende;

        counter++;
    }

    public int getTagID() {return this.tagID; }
    public String getDatum() {return this.datum; }
    public String getAnfang() {return this.anfang; }
    public String getEnde() {return this.ende; }
}
