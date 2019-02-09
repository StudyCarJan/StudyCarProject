import java.util.Random;

public class Nutzer {

    private int nutzerId;

    private String nachname;
    private String vorname;
    private String email;
    private int postleitzahl;
    private String wohnort;
    private String passwort;

    private String studienort;
    private Stundenplan stundenplan;

    private Fahrgemeinschaft fahrgemeinschaft;

    private int bestaetigungscode;

    public Nutzer(String vname, String nname, String mail, String pwort, int plz, String ort,
                  String studort) {
        vorname = vname;
        nachname = nname;
        email = mail;
        postleitzahl = plz;
        wohnort = ort;
        passwort = pwort;
        studienort = studort;

        //nutzerId = ;

        Random a = new Random();
        bestaetigungscode = erstelleCode();
    }

    private int erstelleCode() {
        Random a = new Random();
        int zahl = a.nextInt(99999);
        int digits = String.valueOf(zahl).length();
        //TODO fertig schreiben
        return zahl;
    }
}
