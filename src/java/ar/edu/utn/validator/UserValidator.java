/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.validator;

import ar.edu.utn.entity.User;
import ar.edu.utn.error.OpErrorManagement;
import java.util.regex.Pattern;


/**
 *
 * @author gonza
 */
public class UserValidator {
    
    private OpErrorManagement errorMgmt;
    
    // Me inclino por tener un email validator dado que tiene varios metodos para validarlo.
    private EmailValidator emailValidator;

    public UserValidator(OpErrorManagement errorMgmt, EmailValidator emailValidator) {
        this.errorMgmt = errorMgmt;
        this.emailValidator = emailValidator;
    }
    
    
    
    
    
    private static final String FIRST_NAME_ERROR_MESSAGE  = "El nombre esta vacio";
    private static final int FIRST_NAME_ERROR_NUMBER = 1;
    
    private static final String FIRST_NAME_NOT_CONTAINS_LETTERS_MESSAGE = "El nombre contiene otros caracteres distintos a letras";
    private static final int FIRST_NAME_NOT_CONTAINS_LETTERS_NUMBER = 2;
    
    private static final String LAST_NAME_ERROR_MESSAGE  = "El apellido esta vacio";
    private static final int LAST_NAME_ERROR_NUMBER = 1;
    
    private static final String LAST_NAME_NOT_CONTAINS_LETTERS_MESSAGE = "El apellido contiene otros caracteres distintos a letras";
    private static final int LAST_NAME_NOT_CONTAINS_LETTERS_NUMBER = 2;
    
    private static final String PASSWORD_NOT_VALID_MESSAGE = "La contraseña no es valida";
    private static final int PASSWORD_NOT_VALID_NUMBER = 3;
    
    
    private static final String REGEX_ONLY_LETTERS = "^[a-zA-Z]*$";
    private static final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    
    
    public boolean validateUser(User user) {
        boolean success = true;
        
        if(!validateNames(user)) {
            success = false;
        }
           
        if(!isValidPassword(user)) {
            success = false;
            errorMgmt.addError(PASSWORD_NOT_VALID_NUMBER, PASSWORD_NOT_VALID_MESSAGE);
        }
        
        if(!emailValidator.isEmailValid(user)) {
            success = false;
            errorMgmt.addErrors(emailValidator.getOpErrorMgmt().getErrors());
        }
        
        
                  
        return success;
    }
    /**
     * Valido tanto el nombre como el apellido.
     */
    
    private boolean validateNames(User user) {
        boolean success = true;
        if (!isValidFirstName(user)) {
            success = false;
            errorMgmt.addError(FIRST_NAME_ERROR_NUMBER, FIRST_NAME_ERROR_MESSAGE);
        } else {
            if(!stringContainsLetters(user.getFirstName())) {
                success = false;
                errorMgmt.addError(FIRST_NAME_NOT_CONTAINS_LETTERS_NUMBER, FIRST_NAME_NOT_CONTAINS_LETTERS_MESSAGE);
            }
        }
        
        if(!isValidLastName(user)) {
            success = false;
            errorMgmt.addError(LAST_NAME_ERROR_NUMBER, LAST_NAME_ERROR_MESSAGE);         
        } else {
            if(!stringContainsLetters(user.getLastName())){
                success = false;
                errorMgmt.addError(LAST_NAME_NOT_CONTAINS_LETTERS_NUMBER, LAST_NAME_NOT_CONTAINS_LETTERS_MESSAGE);
            }           
        }
        return success;
    }
    
    
    private boolean isValidFirstName(User user) {
        return !user.getFirstName().isEmpty() && user.getFirstName() != null;
    }
    
    private boolean isValidLastName(User user) {
        return !user.getLastName().isEmpty() && user.getLastName() != null;
    }
    /**
     * Valido que la string que se recibe solamente tenga letras
     * @param string esta string puede ser tanto el nombre como el apellido, por eso no mande un user
     * @return devuelve true en el caso de que solamente se contenga letras
     */
    private boolean stringContainsLetters (String string) {
        return string.matches(REGEX_ONLY_LETTERS);
    }
    
    
    /*  Puede ser que no necesite este validador dado que no necesito la phrase
        private boolean isValidPhrase
    */
    
    private boolean isValidPassword(User user) {
        return user.getPassword().matches(REGEX_PASSWORD);
    }
    /*
    private boolean arePasswordsEqual(User user) {
        
    }*/

    public OpErrorManagement getErrorMgmt() {
        return errorMgmt;
    }
    
    
    
    
}