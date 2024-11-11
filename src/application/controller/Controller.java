package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    public static Forestilling opretForestilling(String navn, LocalDate startDato, LocalDate slutDato) {
        Forestilling forestilling = new Forestilling(navn, startDato, slutDato);
        Storage.addForestilling(forestilling);
        return forestilling;
    }

    public static Kunde opretKunde(String navn, String mobil) {
        Kunde kunde = new Kunde(navn, mobil);
        Storage.addKunde(kunde);
        return kunde;
    }

    public static Plads opretPlads(int række, int nr, int pris, PladsType pladsType) {
        Plads plads = new Plads(række, nr, pris, pladsType);
        Storage.addPlads(plads);
        return plads;
    }

    public static Bestilling opretBestillingMedPladser(Forestilling forestilling, Kunde kunde,
                                                       LocalDate dato, ArrayList<Plads> pladser) {
        // Check om pladser er ledige
        for (Plads plads : pladser) {
            if(!forestilling.erPladsLedig(plads.getRække(), plads.getNr(), dato)) {
                return null;
            }
        }

        // Check om bestillingen er i den periode hvor forestillingen vises.
        if(dato.isBefore(forestilling.getStartDato()) || dato.isAfter(forestilling.getSlutDato())) {
            return null;
        }

        // Ellers lav en bestilling og add pladser til den.
        Bestilling bestilling = forestilling.createBestilling(dato, kunde);
        for (Plads plads : pladser) {
            bestilling.addPlads(plads);
        }
        return bestilling;
    }
}
