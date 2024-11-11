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
}
