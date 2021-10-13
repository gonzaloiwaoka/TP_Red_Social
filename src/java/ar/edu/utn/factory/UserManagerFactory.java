/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.factory;

import ar.edu.utn.dao.PersonDao;
import ar.edu.utn.dao.UserDao;
import ar.edu.utn.error.OpErrorManagement;
import ar.edu.utn.manager.UserManager;

import ar.edu.utn.mapper.UserMapper;
import ar.edu.utn.validator.EmailValidator;
import ar.edu.utn.validator.UserValidator;

/**
 *
 * @author gonza
 */
public class UserManagerFactory {
    
    public static UserManager createUserManager() {
        // TODO - pasarlo a un archivo
        try {
            //https://www.postgresql.org/docs/7.4/jdbc-use.html
            Class.forName("org.postgresql.Driver");
            
        } catch (Exception ex) {
            
        }
        String dbHost = "chunee.db.elephantsql.com";
        String dbPort = "5432";
        String user = "kwigoeeo";
        String password = "Lf6aC_Tp82FDiPRT6JfWLhBs3t80sKXT";
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/";
        
        UserDao userDao = new UserDao(connectionString, user, password, new OpErrorManagement());
        PersonDao personDao = new PersonDao(connectionString, user, password, new OpErrorManagement());
        
        UserMapper userMapper = new UserMapper(userDao, personDao, new OpErrorManagement());
        EmailValidator emailValidator = new EmailValidator(new OpErrorManagement(), userMapper);
        UserValidator userValidator = new UserValidator(new OpErrorManagement(), emailValidator);
        
        
        UserManager userManager = new UserManager(userMapper, userValidator, new OpErrorManagement());
        return userManager;
    }
    
}
