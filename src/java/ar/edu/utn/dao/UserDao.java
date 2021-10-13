/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.dao;

import ar.edu.utn.error.OpErrorManagement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gonza
 */
public class UserDao extends BaseDao{
    private static final String TABLE = "app_user";
    
    public UserDao(String connectionString, String user, String password, OpErrorManagement opErrorMgmt) {
        super(connectionString, user, password, opErrorMgmt);
    }
    
    // TODO - La inserción podría ser genérica se se emplea key / values
    
    public int insert(Map<Integer, Object> parameters) {
        String sql = "INSERT INTO " + TABLE + 
            "(email_user, password_user, activated_user) VALUES (?, ?, ?)";
        return write(sql, parameters);
    }
    
    // TODO - La inserción puede ser genérica
    
    public List<Map<String, Object>> get(Map<Integer, Object> parameters) {
        String sql = "SELECT * FROM " + TABLE + " where email_user = ?";
        return read(sql, parameters);
    }
}
