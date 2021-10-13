/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.error;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gonza
 */
public class OpErrorManagement {
    private List<OpError> errors;
    
    public List<OpError> getErrors() {
        if (errors == null) {
            errors = new ArrayList();
        }
        return errors;
    }
    
    public void addError(int number, String message) {
        getErrors().add(new OpError(number, message));  
    }
    
    public void addError(OpError opError) {
        getErrors().add(opError);
    }
    
    public void addErrors(List<OpError> errors) {
        getErrors().addAll(errors);
    }
    
}
