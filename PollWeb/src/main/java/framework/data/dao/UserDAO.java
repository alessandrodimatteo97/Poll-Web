/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.User;

/**
 *
 * @author davide
 */
public interface UserDAO {
    
    User createUser();
    
    User createUser(ResultSet rs) /* throws DataException */;
    
    User createResponsibleUser();
    
    User createResponsibleUser(ResultSet rs) /* throws DataException */;
    
    User createPartecipantUser();
    
    User createPartecipantUser(ResultSet rs) /* throws DataException */;
        
    boolean deleteUser(User user);
    
    boolean changePassword(User user, String pwd); /* controllare la password se deve essere di un altro tipo per questioni di sicurezza*/
    
    boolean setAdmin(User user);
    
    boolean setIP(User user, String IP);
    
    boolean changeEmail(User user, String email);
    
    User getUserById(int userId);
    
    List<User> getUsers();
       
}
