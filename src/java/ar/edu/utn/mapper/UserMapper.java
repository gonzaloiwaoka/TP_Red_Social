/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.mapper;

import ar.edu.utn.dao.PersonDao;
import ar.edu.utn.dao.UserDao;
import ar.edu.utn.entity.User;
import ar.edu.utn.error.OpError;
import ar.edu.utn.error.OpErrorManagement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;

import java.util.Map;
import java.util.Set;


/**
 *
 * @author gonza
 */
public class UserMapper {
    
    private UserDao userDao;
    private PersonDao personDao;
    private OpErrorManagement opErrorMgmt;
    
    public UserMapper(UserDao userDao, PersonDao personDao, OpErrorManagement opErrorMgmt) {
        this.userDao = userDao;
        this.personDao = personDao;
        this.opErrorMgmt = opErrorMgmt;
    }
    
    public boolean save(User user) {
        boolean success = true;
       
        try {
           
            Map<Integer, Object> userMap = translateUser(user);
            int idUser = userDao.insert(userMap);
            
            Map<Integer, Object> personMap = translatePerson(idUser, user);
            int idPerson = personDao.insert(personMap);
            
            // TODO - controlar errores
        } catch (Exception ex) {
            // TODO - hacer algo
        } finally {
            return success;
        }
    }
    
    public List<User> getUser(String email) {
        
        Map<Integer, Object> emailMap = translateEmail(email);
        
        List<Map<String, Object>> parameters = userDao.get(emailMap);
        
        List<User> users = translateToUsers(parameters);
        
        opErrorMgmt.addErrors(userDao.getOpErrorMgmt().getErrors());
        
        return users;
    }
    
    private HashMap<Integer, Object> translateEmail(String email) {
        int i = 1;
        HashMap<Integer, Object> parameters = new HashMap<>();
        
        parameters.put(1, email);
        
        return parameters;
    }
    
    
    private HashMap<Integer, Object> translateUser(User user) {

        int i = 1;
        HashMap<Integer, Object> parameters = new HashMap<>();
        parameters.put(i++, user.getEmail());
        parameters.put(i++, user.getPassword());
        parameters.put(i++, user.isActivated());

        return parameters;
    }
    
    private HashMap<Integer, Object> translatePerson(int id, User user) {
        int i = 1;
        HashMap<Integer, Object> parameters = new HashMap<>();
        parameters.put(i++, user.getFirstName());
        parameters.put(i++, user.getLastName());
        parameters.put(i++, user.getPhrase());
        parameters.put(i++, user.isPublicProfile());
        parameters.put(i++, id);
        return parameters;
    }
    
    private List<User> translateToUsers(List<Map<String, Object>> parameters) {
        List<User> users = new ArrayList<>();
        
        for (Map<String, Object> element : parameters) {
            User user = new User();
            users.add(user);
        }        
        return users;
    }

    public OpErrorManagement getOpErrorMgmt() {
        return opErrorMgmt;
    }

    
       
    
}
    

