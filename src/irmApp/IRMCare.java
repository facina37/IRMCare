/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmApp;

import irmApp.model.Patient;
import irmApp.model.Examen;
import irmApp.view.AjoutExamenController;
import irmApp.view.ListePatientsTechnicienController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.*;



/**
 *
 * @author Laure
 */
public class IRMCare extends Application {
    
    private Stage primaryStage;
    private Patient patient;
    private Examen examen;
    public IRMCare() {

    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IRMCare");
        //premiere page affichée
        showListePatientsTechnicien();
    }
    
    /**
     * Retourne scene
     * @return primariStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void showListePatientsTechnicien() {
        try {
            // charge table
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(IRMCare.class.getResource("view/listePatientsTechnicien.fxml"));
            TabPane studentTable = (TabPane) loader.load();
            
            // affiche scene
            Scene scene = new Scene(studentTable);
            primaryStage.setScene(scene);
            primaryStage.show();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //Qd on sélectionne un patient et que l'on veut lui ajouter un Examen
    public boolean showExamForm(Patient aPatient) {
        patient = aPatient;
        try {
            // nouvelle scene pour pop up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(IRMCare.class.getResource("view/ajoutExamen.fxml"));
            GridPane page = (GridPane) loader.load();

            // Creer nouvelle scene.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
        
            // dialogue avec le controller
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
    
    //Qd on sélectionne un patient et que l'on veut lui ajouter un Examen
    public boolean showVisiteForm(Patient aPatient) {
        patient = aPatient;
        try {
            // nouvelle scene pour pop up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(IRMCare.class.getResource("view/ajoutVisite.fxml"));
            GridPane page = (GridPane) loader.load();

            // Creer nouvelle scene.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
        
            // dialogue avec le controller
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
    
    public boolean showVerifIRMForm(Examen examen) {
        this.examen = examen;
        try {
            // nouvelle scene pour pop up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(IRMCare.class.getResource("view/examen_perso.fxml"));
            GridPane page = (GridPane) loader.load();

            // Creer nouvelle scene.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
        
            // dialogue avec le controller
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
    
    public Patient getPatient()
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
