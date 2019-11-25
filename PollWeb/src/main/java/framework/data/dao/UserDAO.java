/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public interface UserDAO {
    
    ResponsibleUser createUser();
    
    ResponsibleUser createUser(ResultSet rs);
    
    ResponsibleUser createResponsibleUser();
    
    ResponsibleUser createResponsibleUser(ResultSet rs);
    
    ResponsibleUser createPartecipantUser();
    
    ResponsibleUser createPartecipantUser(ResultSet rs);
        
    boolean deleteUser(ResponsibleUser user);
    
    boolean changePassword(ResponsibleUser user, String pwd); /* controllare la password se deve essere di un altro tipo per questioni di sicurezza*/
    
    boolean setAdmin(ResponsibleUser user);
    
    boolean setIP(ResponsibleUser user, String IP);
    
    boolean changeEmail(ResponsibleUser user, String email);
    
    ResponsibleUser getUserById(int userId);
    
    List<ResponsibleUser> getUsers();
       
}
