package gui;

import application.model.Forestilling;
import application.model.Kunde;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartWindow extends Application {
    private ForestillingPane forestillingPane;
    private KundePane kundePane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Teater bestillinger");

        HBox startWindow = new HBox();
        initContent(startWindow);

        Scene scene = new Scene(startWindow);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initContent(HBox startWindow) {
        // Layout
        startWindow.setPadding(new Insets(20,20,20,20));
        startWindow.setSpacing(20);

        // Get panes
        forestillingPane = new ForestillingPane();
        kundePane = new KundePane();
        GridPane bestillingPane = new BestillingPane(this);
        startWindow.getChildren().addAll(forestillingPane, kundePane, bestillingPane);


    }

    public Forestilling getSelectedForestilling() {
        return forestillingPane.getSelectedForestilling();
    }

    public Kunde getSelectedKunde() {
        return kundePane.getSelectedKunde();
    }

    public void clearForestillingPane() {
        for (Node child : forestillingPane.getChildren()) {
            if (child instanceof TextField) {
                ((TextField) child).clear();
            }
        }
    }
    
    public void clearKundePane() {
        for (Node child : kundePane.getChildren()) {
            if(child instanceof TextField) {
                ((TextField) child).clear();
            }
        }
    }
}
