/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmcare;

import javafx.fxml.FXML;

/**
 *
 * @author Laure
 */
public class Patient {
    private String id;
    private String groupe;
    private String firstName;
    private String lastName;
    private String age;
    private String sexe;
    private String grade;
    private String statut;
    
    public Patient(int idP, int groupeP, String firstNameP, String lastNameP,
            int ageP, boolean excluP, boolean finiP, char sexeP, int gradeP){
        id = Integer.toString(idP);
        groupe = Integer.toString(groupeP);
        firstName = firstNameP;
        lastName = firstNameP;
        age = Integer.toString(ageP);
        sexe = Character.toString(sexeP);
        grade = Integer.toString(gradeP);
        if(finiP == true)
            statut = "Programme fini";
        else if(excluP == true)
            statut = "Exclu du programme";
        else statut = "Dans le programme";
    }
    
    public String getId(){
        return id;
    }
}
