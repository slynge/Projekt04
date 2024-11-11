package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forestilling {
    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private final ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Forestilling(String navn, LocalDate startDato, LocalDate slutDato) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public ArrayList<Bestilling> getBestillinger() {
        return new ArrayList<>(bestillinger);
    }

    public Bestilling createBestilling(LocalDate dato, Kunde kunde) {
        Bestilling bestilling = new Bestilling(dato, kunde, this);
        bestillinger.add(bestilling);
        return bestilling;
    }

    public boolean erPladsLedig(int rækkeNr, int pladsNr, LocalDate dato) {
        for (Bestilling bestilling : bestillinger) {
            if(bestilling.getDato().equals(dato)) {
                for (Plads plads : bestilling.getPladser()) {
                    if(plads.getRække() == rækkeNr && plads.getNr() == pladsNr) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Navn: %-17s Spiller fra %s til %s", navn, startDato, slutDato);
    }

    public ArrayList<Plads> getOptagetPladser() {
        ArrayList<Plads> optagetPladser = new ArrayList<>();
        for (Bestilling bestilling : bestillinger) {
            optagetPladser.addAll(bestilling.getPladser());
        }
        return optagetPladser;
    }

    public int antalBestiltePladserPåDag(LocalDate dato) {
        int antalPladser = 0;
        for (Bestilling bestilling : bestillinger) {
            if(bestilling.getDato().equals(dato)) {
                antalPladser += bestilling.getPladser().size();
            }
        }
        return antalPladser;
    }

    public LocalDate succesDato() {
        int højesteAntalPladser = 0;
        LocalDate succesDato = startDato;
        for(LocalDate dato = startDato; dato.isAfter(slutDato); dato = dato.plusDays(1)) {
            int antalPladser = antalBestiltePladserPåDag(dato);
            if(antalPladser > højesteAntalPladser) {
                succesDato = dato;
                højesteAntalPladser = antalPladser;
            }
        }
        return succesDato;
    }

}
