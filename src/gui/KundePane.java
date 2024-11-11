package gui;

import application.controller.Controller;
import application.model.Forestilling;
import application.model.Kunde;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class KundePane extends GridPane {
    private final ListView<Kunde> kundeListView;
    private final TextField navnTextField;
    private final TextField mobilTextField;
    private final Label errorLabel;

    public KundePane() {
        this.setVgap(10);
        this.setHgap(10);

        Label kundeLabel = new Label("Kunder");
        this.add(kundeLabel, 0, 0);

        kundeListView = new ListView<>();
        kundeListView.getItems().setAll(Storage.getKunder());
        ChangeListener<Kunde> listener = (ov, oldKunde, newKunde) -> this.selectedKundeChanged(newKunde);
        kundeListView.getSelectionModel().selectedItemProperty().addListener(listener);
        this.add(kundeListView, 0, 1, 2, 1);

        Label navnLabel = new Label("Kunde navn");
        navnTextField = new TextField();
        this.addRow(2, navnLabel, navnTextField);

        Label mobilLabel = new Label("Kunde mobil");
        mobilTextField = new TextField();
        this.addRow(3, mobilLabel, mobilTextField);

        Button opretKundeButton = new Button("Opret kunde");
        this.add(opretKundeButton, 1, 4);
        opretKundeButton.setOnAction(event -> opretKundeAction());

        errorLabel = new Label();
        this.add(errorLabel, 0, 5, 2, 1);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void opretKundeAction() {
        String navn = navnTextField.getText().trim();
        String mobil = mobilTextField.getText().trim();

        if(navn.isEmpty() || mobil.isEmpty()) {
            errorLabel.setText("Navn/mobil er tomt.");
            return;
        }

        Controller.opretKunde(navn, mobil);
        kundeListView.getItems().setAll(Storage.getKunder());

        navnTextField.clear();
        mobilTextField.clear();
        errorLabel.setText("");
    }

    public Kunde getSelectedKunde() {
        return kundeListView.getSelectionModel().getSelectedItem();
    }

    private void selectedKundeChanged(Kunde newKunde) {
        this.updateControls(newKunde);
    }

    private void updateControls(Kunde newKunde) {
        if(newKunde != null) {
            navnTextField.setText(newKunde.getNavn());
            mobilTextField.setText(newKunde.getMobil());
        }
    }
}