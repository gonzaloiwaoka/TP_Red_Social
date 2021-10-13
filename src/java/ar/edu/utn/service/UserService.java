/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.service;

import ar.edu.utn.entity.User;
import ar.edu.utn.error.OpErrorManagement;
import ar.edu.utn.factory.UserManagerFactory;
import ar.edu.utn.manager.UserManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author gonza
 */
@Path("/createUser")
public class UserService {
    
    private UserManager userManager;
    private OpErrorManagement opErrorMgmt;
    // Hago un new por el simple hecho de probarlo, muy probablemente implemente un factory
    public UserService() {
        this.userManager = UserManagerFactory.createUserManager();
        opErrorMgmt = new OpErrorManagement();
    }
       
    
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/userCreation")
    public Response createUser(String body) {
        boolean success = true;
        String responseString = null;
        Response.Status statusType = null;
        try {
            // crear Manager =
            Gson gson = new GsonBuilder().create();    
            User user = gson.fromJson(body, User.class);
                    
            success = userManager.save(user);
            
            if(success) {
                //userManager.save(user);
                responseString = "{\"status\": \"OK!\"}";
                statusType = Response.Status.OK;
            } else {
                opErrorMgmt.addErrors(userManager.getOpErrorMgmt().getErrors());
                StringBuilder errors = new StringBuilder();
                for (int i = 0; i < opErrorMgmt.getErrors().size(); i++) {
                    
                    errors.append(opErrorMgmt.getErrors().get(i).getMessage());                                      
                }
                statusType = Response.Status.OK;
                responseString = gson.toJson(errors.toString(), String.class);
            }               
        } catch (Exception ex) {
            responseString = "Error, hubo una excepcion " + ex.getMessage();
            statusType = Response.Status.INTERNAL_SERVER_ERROR;
        } finally {
            return Response.status(statusType).entity(responseString).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/something")
    public Response getSimple() {
        String responseString = null;
        Response.Status statusType = null;
        
        try {
            // Los factories ayudan en la abstracción
            String newThing = "Asdfg";
            
            // Lo más prolijo sería utiliar un traductor (un helper por ejemplo 
            // o un objeto dedicado a generar dto)
            Gson gson = new GsonBuilder().create();
            responseString = gson.toJson(newThing);
            statusType = Response.Status.OK;
            
        } catch (Exception exception) {
            responseString = "MAI GAAAAAD!!! explotó todo! \n" + exception.getMessage();
            statusType = Response.Status.INTERNAL_SERVER_ERROR;

        } finally {
            return Response.status(statusType).entity(responseString).build();
        }
    }
}
