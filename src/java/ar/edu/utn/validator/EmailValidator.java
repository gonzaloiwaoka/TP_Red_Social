/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.validator;


import ar.edu.utn.entity.User;
import ar.edu.utn.error.OpErrorManagement;
import ar.edu.utn.mapper.UserMapper;
import java.util.regex.Pattern;

/**
 *
 * @author gonza
 */
public class EmailValidator {
    private UserMapper userMapper;
    private OpErrorManagement opErrorMgmt;

    public EmailValidator(OpErrorManagement opErrorMgmt, UserMapper userMapper) {
        this.opErrorMgmt = opErrorMgmt;
        this.userMapper = userMapper;
    }
    
    
    
    
    private final static String EMAIL_NOT_VALID_MESSAGE = "El email ingresado no es valido";
    private static final int EMAIL_NOT_VALID_NUMBER = 4;
    
    private final static String EMAIL_ALREADY_EXISTS_MESSAGE = "El email ya esta en uso";
    private final static int EMAIL_ALREADY_EXISTS_NUMBER = 10;
    
    private final static String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
    
    public boolean isEmailValid(User user) {
        boolean success = true;
        System.out.println(emailFormatValidator(user));
        if (!emailFormatValidator(user)) {
            success = false;
            opErrorMgmt.addError(EMAIL_NOT_VALID_NUMBER, EMAIL_NOT_VALID_MESSAGE);
        }
        
        if (!emailExists(user)) {
            success = false;
        }
        
        
        return success;
    }
    
    private boolean emailFormatValidator(User user) {
       return user.getEmail().matches(REGEX_EMAIL);
    }
    
    /*
    private boolean areEmailsEqual(User user) {
        
    }
    */
    
    private boolean emailExists(User user) {
        boolean success = true;
        if(!userMapper.getUser(user.getEmail()).isEmpty()) {
            success = false;
            opErrorMgmt.addError(EMAIL_ALREADY_EXISTS_NUMBER, EMAIL_ALREADY_EXISTS_MESSAGE);
        }
        return success;
    }

    public OpErrorManagement getOpErrorMgmt() {
        return opErrorMgmt;
    }
        
    
    
    
}
