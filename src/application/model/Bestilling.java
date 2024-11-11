package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bestilling {
    private LocalDate dato;
    private Kunde kunde;
    private final ArrayList<Plads> pladser = new ArrayList<>();
    private Forestilling forestilling;

    Bestilling(LocalDate dato, Kunde kunde, Forestilling forestilling) {
        this.dato = dato;
        this.kunde = kunde;
        this.forestilling = forestilling;
        kunde.addBestilling(this);
    }

    public LocalDate getDato() {
        return dato;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public ArrayList<Plads> getPladser() {
        return new ArrayList<>(pladser);
    }

    public Forestilling getForestilling() {
        return forestilling;
    }

    public void addPlads(Plads plads) {
        if(!pladser.contains(plads)) {
            pladser.add(plads);
        }
    }

    @Override
    public String toString() {
        return String.format("Bestilling til forestillingen %s på dato %s med henholdsvis %d billetter.", forestilling.getNavn(), dato, pladser.size());
    }
}
