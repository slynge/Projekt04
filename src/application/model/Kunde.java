package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Kunde {
    private String navn;
    private String mobil;
    private final ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Kunde(String navn, String mobil) {
        this.navn = navn;
        this.mobil = mobil;
    }

    public String getNavn() {
        return navn;
    }

    public String getMobil() {
        return mobil;
    }

    public ArrayList<Bestilling> getBestillinger() {
        return new ArrayList<>(bestillinger);
    }

    public void addBestilling(Bestilling bestilling) {
        if(!bestillinger.contains(bestilling)) {
            bestillinger.add(bestilling);
        }
    }

    public ArrayList<Plads> bestiltePladserTilForestillingPåDag(Forestilling forestilling, LocalDate dato) {
        ArrayList<Plads> pladser = new ArrayList<>();
        for (Bestilling bestilling : bestillinger) {
            if(bestilling.getForestilling().equals(forestilling) && bestilling.getDato() == dato) {
                pladser.addAll(bestilling.getPladser());
            }
        }
        return pladser;
    }

    @Override
    public String toString() {
        return String.format("Navn: %-17s Mobil: %s", navn, mobil);
    }
}
