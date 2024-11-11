package gui;

import application.controller.Controller;
import application.model.Bestilling;
import application.model.Forestilling;
import application.model.Plads;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class ForestillingPane extends GridPane {
    private final TextField navnTextField;
    private final TextField startDatoTextField;
    private final TextField slutDatoTextField;
    private final ListView<Forestilling> forestillingListView;
    private final Label errorLabel;

    public ForestillingPane(StartWindow startWindow) {
        this.setVgap(10);

        Label forestillingLabel = new Label("Forestillinger");
        this.add(forestillingLabel, 0, 0);

        forestillingListView = new ListView<>();
        forestillingListView.getItems().setAll(Storage.getForestillinger());
        ChangeListener<Forestilling> listener = (ov, oldForestilling, newForestilling) -> this.selectedForestillingChanged(newForestilling, startWindow);
        forestillingListView.getSelectionModel().selectedItemProperty().addListener(listener);
        this.add(forestillingListView, 0, 1, 2, 1);

        Label navnLabel = new Label("Navn");
        navnTextField = new TextField();
        this.addRow(2, navnLabel, navnTextField);

        Label startDatoLabel = new Label("Start dato");
        startDatoTextField = new TextField();
        this.addRow(3, startDatoLabel, startDatoTextField);

        Label slutDatoLabel = new Label("Slut dato");
        slutDatoTextField = new TextField();
        this.addRow(4, slutDatoLabel, slutDatoTextField);

        Button opretForestillingButton = new Button("Opret forestilling");
        opretForestillingButton.setOnAction(event -> opretForestillingAction());
        this.add(opretForestillingButton, 1, 5);

        errorLabel = new Label();
        this.add(errorLabel, 0, 6,2,1);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void opretForestillingAction() {
        String navn = navnTextField.getText().trim();

        if(navn.isEmpty() || startDatoTextField.getText().isEmpty() || slutDatoTextField.getText().isEmpty()) {
            errorLabel.setText("Navn/startdato/slutdato er tomt.");
            return;
        }
        LocalDate startDato = LocalDate.parse(startDatoTextField.getText().trim());
        LocalDate slutDato = LocalDate.parse(slutDatoTextField.getText().trim());


        Controller.opretForestilling(navn, startDato, slutDato);
        forestillingListView.getItems().setAll(Storage.getForestillinger());

        navnTextField.clear();
        startDatoTextField.clear();
        slutDatoTextField.clear();
        errorLabel.setText("");
    }

    private void selectedForestillingChanged(Forestilling newForestilling, StartWindow startWindow) {
        this.updateControls(newForestilling, startWindow);
    }

    public Forestilling getSelectedForestilling() {
        return forestillingListView.getSelectionModel().getSelectedItem();
    }

    private void updateControls(Forestilling forestilling, StartWindow startWindow) {
        if(forestilling != null) {
            navnTextField.setText(forestilling.getNavn());
            startDatoTextField.setText(forestilling.getStartDato().toString());
            slutDatoTextField.setText(forestilling.getSlutDato().toString());
            ArrayList<Plads> ledigePladser = Storage.getPladser();
            ledigePladser.removeAll(forestilling.getOptagetPladser());
            startWindow.getBestillingsPane().setPladsListView(ledigePladser);
        }
    }

}
