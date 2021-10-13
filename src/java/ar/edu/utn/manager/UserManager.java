/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.manager;

import ar.edu.utn.entity.User;
import ar.edu.utn.error.OpErrorManagement;
import ar.edu.utn.mapper.UserMapper;
import ar.edu.utn.validator.UserValidator;
import java.util.List;

/**
 *
 * @author gonza
 */
public class UserManager {
    private UserMapper userMapper;
    private UserValidator userValidator;
    private OpErrorManagement opErrorMgmt;

    public UserManager(UserMapper userMapper, UserValidator userValidator, OpErrorManagement opErrorMgmt) {
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.opErrorMgmt = opErrorMgmt;
    }
    
    
    
    
    public boolean save(User user){
        boolean success = true;
        
        try {
            success = userValidator.validateUser(user);
            
            if(success) {
                userMapper.save(user);
            } else {
                // Guardar errores del validator
                opErrorMgmt.addErrors(userValidator.getErrorMgmt().getErrors());
            }
            
            if(!success) {
                // guardar errores del mapper
            }
            
        } catch (Exception ex) {
            
        } finally {
            return success;
        }
       
    }
    
    public boolean changeProfile(User user) {
        return true;
    }
    
    public boolean restorePassword(String email) {
        return true;
    }
    
    public User getUser(String username) {
        return new User();
    }

    public OpErrorManagement getOpErrorMgmt() {
        return opErrorMgmt;
    }
    
    
}
