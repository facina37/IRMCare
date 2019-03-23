/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmApp.model;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Laure
 */
public class Examen {
    private final StringProperty id;
    private final StringProperty date;
    private final StringProperty firstName;
    private final StringProperty lastName;
    
    public Examen(int idP, LocalDate dateP, String firstNameP, String lastNameP){
        id = new SimpleStringProperty(Integer.toString(idP));
        date = new SimpleStringProperty(Integer.toString(dateP.getDayOfMonth())+"/"+Integer.toString(dateP.getMonthValue())+"/"+Integer.toString(dateP.getYear()));
        firstName = new SimpleStringProperty(firstNameP);
        lastName = new SimpleStringProperty(lastNameP);
    }
    
    public StringProperty idProperty() {
        return id;
    }
    public StringProperty dateProperty() {
        return date;
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public int getId() {
        return Integer.parseInt(id.get());
    }
}
