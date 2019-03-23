/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmcare;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Laure
 */
public class IRMCare extends Application {
    
    private Stage primaryStage;
    private Patient patient;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("listePatientsTechnicien.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Retourne scene
     * @return primariStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    //Qd on sélectionne un patient et que l'on veut lui ajouter un Examen
    public boolean showExamForm(Patient patient) {
        try {
            this.patient = patient;
            // nouvelle scene pour pop up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(IRMCare.class.getResource("ajoutExamen.fxml"));
            GridPane page = (GridPane) loader.load();

            // Creer nouvelle scene.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
        
            // etudiant dans le controller
            AjoutExamenController controller = loader.getController();
            controller.setDialogStage(dialogStage);
        
            // montre pop up et attend que close soit clique
            dialogStage.showAndWait();
        
            return controller.isOkClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Patient getPatient ()
    {
        return patient;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
