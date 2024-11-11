package gui;

import application.controller.Controller;
import application.model.Forestilling;
import application.model.Kunde;
import application.model.PladsType;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);

        testPrint();
    }

    private static void initStorage() {
        // Forestillinger
        Controller.opretForestilling("Evita", LocalDate.of(2023, 8, 10), LocalDate.of(2023,8,20));
        Controller.opretForestilling("Lykke Per", LocalDate.of(2023, 9, 1), LocalDate.of(2023,9,10));
        Controller.opretForestilling("Chess", LocalDate.of(2023,8,21), LocalDate.of(2023,8,30));

        // Kunder
        Controller.opretKunde("Anders Hansen", "11223344");
        Controller.opretKunde("Peter Jensen", "12345678");
        Controller.opretKunde("Niels Madsen", "12341234");

        // Pladser
        for(int rækkeNr = 1; rækkeNr < 16; rækkeNr++) {
            for(int pladsNr = 1; pladsNr < 21; pladsNr++) {
                // Grønne pladser
                if(rækkeNr < 6 && (pladsNr < 3 || pladsNr > 18)) {
                    Controller.opretPlads(rækkeNr, pladsNr, 450, PladsType.STANDARD);
                }

                else if((rækkeNr > 5 && rækkeNr < 11) && (pladsNr > 2 && pladsNr < 19)) {
                    if(rækkeNr == 10 && (pladsNr > 7 && pladsNr < 13)) {
                        Controller.opretPlads(rækkeNr, pladsNr, 450, PladsType.KØRESTOL);
                    }
                    else {
                        Controller.opretPlads(rækkeNr, pladsNr, 450, PladsType.STANDARD);
                    }
                }

                // Gule pladser
                else if(rækkeNr < 6 && (pladsNr > 2 && pladsNr < 19)) {
                    Controller.opretPlads(rækkeNr, pladsNr, 500, PladsType.STANDARD);
                }

                // Blå pladser
                else {
                    if(rækkeNr == 11 && (pladsNr > 7 && pladsNr < 13)) {
                        Controller.opretPlads(rækkeNr, pladsNr, 400, PladsType.EKSTRABEN);
                    }
                    else {
                        Controller.opretPlads(rækkeNr, pladsNr, 400, PladsType.STANDARD);
                    }
                }
            }
        }
    }

    private static void testPrint() {
        for (Forestilling forestilling : Storage.getForestillinger()) {
            System.out.println(forestilling);
        }

        System.out.println();

        for (Kunde kunde : Storage.getKunder()) {
            System.out.println(kunde);
        }

        System.out.println();

        for(int rækkeNr = 0; rækkeNr < 15; rækkeNr++) {
            for(int pladsNr = 0; pladsNr < 20; pladsNr++) {
                System.out.printf("%-65s", Storage.getPladser().get((rækkeNr * 20) + pladsNr));
            }
            System.out.println("\n");
        }


    }
}
