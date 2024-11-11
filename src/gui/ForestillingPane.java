package gui;

import application.controller.Controller;
import application.model.Forestilling;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.time.LocalDate;

public class ForestillingPane extends GridPane {
    private final TextField navnTextField;
    private final TextField startDatoTextField;
    private final TextField slutDatoTextField;
    private final ListView<Forestilling> forestillingListView;
    private final Label errorLabel;

    public ForestillingPane() {
        this.setVgap(10);

        Label forestillingLabel = new Label("Forestillinger");
        this.add(forestillingLabel, 0, 0);

        forestillingListView = new ListView<>();
        forestillingListView.getItems().setAll(Storage.getForestillinger());
        ChangeListener<Forestilling> listener = (ov, oldForestilling, newForestilling) -> this.selectedForestillingChanged(newForestilling);
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
        this.add(errorLabel, 0, 5);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void opretForestillingAction() {
        String navn = navnTextField.getText().trim();
        LocalDate startDato = LocalDate.parse(startDatoTextField.getText().trim());
        LocalDate slutDato = LocalDate.parse(slutDatoTextField.getText().trim());

        if(navn.isEmpty()) {
            errorLabel.setText("Navn er tomt.");
            return;
        }

        Controller.opretForestilling(navn, startDato, slutDato);
        forestillingListView.getItems().setAll(Storage.getForestillinger());

        navnTextField.clear();
        startDatoTextField.clear();
        slutDatoTextField.clear();
        errorLabel.setText("");
    }

    private void selectedForestillingChanged(Forestilling newForestilling) {
        this.updateControls(newForestilling);
    }

    public Forestilling getSelectedForestilling() {
        return forestillingListView.getSelectionModel().getSelectedItem();
    }

    private void updateControls(Forestilling forestilling) {
        if(forestilling != null) {
            navnTextField.setText(forestilling.getNavn());
            startDatoTextField.setText(forestilling.getStartDato().toString());
            slutDatoTextField.setText(forestilling.getSlutDato().toString());
        }
    }

}
