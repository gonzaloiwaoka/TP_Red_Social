/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.dao;

import ar.edu.utn.error.OpErrorManagement;
import java.util.Map;

/**
 *
 * @author gonza
 */
public class PersonDao extends BaseDao {
    
    private static final String TABLE = "person";
    
    public PersonDao(String connectionString, String user, String password, OpErrorManagement opErrorMgmt) {
        super(connectionString, user, password, opErrorMgmt);
    }
           
    public int insert(Map<Integer, Object> parameters) {
        String sql = "INSERT INTO " + TABLE + 
            "(first_name_person, last_name_person, phrase_person, public_profile_person, id_user_person) VALUES (?, ?, ?, ?, ?)";
        return write(sql, parameters);
    }
    
    
}
