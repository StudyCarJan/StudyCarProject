import java.util.Random;

public class Nutzer {

    private int nutzerId;
    private static int counter = 1;

    private String nachname;
    private String vorname;
    private String email;
    private int postleitzahl;
    private String wohnort;
    private String passwort;

    private String studienort;
    private Stundenplan stundenplan;

    private Fahrgemeinschaft fahrgemeinschaft;

    private String bestaetigungscode;

    public Nutzer(String vname, String nname, String mail, String pwort, int plz, String ort,
                  String studort) {
        vorname = vname;
        nachname = nname;
        email = mail;
        postleitzahl = plz;
        wohnort = ort;
        passwort = pwort;
        studienort = studort;

        nutzerId = counter;
        counter++;

        Random a = new Random();
        bestaetigungscode = erstelleCode();
    }

    private String erstelleCode() {
        Random a = new Random();
        int zahl = a.nextInt(99999);
        String s = String.valueOf(zahl);
        int digits = String.valueOf(zahl).length();

        while(s.length() < 5-digits) {
            s = "0" + s;
        }

        return s;
    }
}