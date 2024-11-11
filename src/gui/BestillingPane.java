package gui;

import application.controller.Controller;
import application.model.Bestilling;
import application.model.Forestilling;
import application.model.Kunde;
import application.model.Plads;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class BestillingPane extends GridPane {
    private ListView<Plads> pladsListView;
    private TextField datoTextField;
    private Label errorLabel;

    public BestillingPane(StartWindow startWindow) {
        this.setVgap(10);
        this.setHgap(10);

        Label pladsLabel = new Label("Pladser");
        this.add(pladsLabel, 0, 0);
        pladsListView = new ListView<>();
        pladsListView.getItems().setAll(Storage.getPladser());
        pladsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.add(pladsListView, 0, 1, 2, 1);

        Label datoLabel = new Label("Dato");
        datoTextField = new TextField();
        this.addRow(2, datoLabel, datoTextField);

        Button opretBestillingButton = new Button("Opret bestilling");
        opretBestillingButton.setOnAction(event -> opretBestillingAction(startWindow));
        this.add(opretBestillingButton, 1, 3);

        errorLabel = new Label();
        this.add(errorLabel, 0, 3);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void opretBestillingAction(StartWindow startWindow) {
        Forestilling forestilling = startWindow.getSelectedForestilling();
        Kunde kunde = startWindow.getSelectedKunde();

        ArrayList<Plads> pladser = new ArrayList<>(pladsListView.getSelectionModel().getSelectedItems());
        LocalDate dato = LocalDate.parse(datoTextField.getText());
        if(forestilling == null || kunde == null || pladser.isEmpty() || dato.toString().isEmpty()) {
            errorLabel.setText("Information mangler.");
        }
        Bestilling bestilling = Controller.opretBestillingMedPladser(forestilling, kunde,
                dato, pladser);

        if(bestilling != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Bestillingsbekræftelse");
            alert.setContentText(bestilling.toString());
            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Bestillingsfejl");
            alert.setContentText("Bestillingen var ikke mulig, da datoen for bestillingen ikke var i forestillingens periode.");
            alert.showAndWait();
        }

        startWindow.clearForestillingPane();
        startWindow.clearKundePane();
        datoTextField.clear();

    }
}
