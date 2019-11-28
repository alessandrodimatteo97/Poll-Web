/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public interface ResponsibleUserDAO {
       
    ResponsibleUser createResponsibleUser();
    
    ResponsibleUser createResponsibleUser(ResultSet rs);
    
    boolean deleteResponsibleUser(int UserKey);
        
    boolean changePassword(ResponsibleUser user, String pwd); /* controllare la password se deve essere di un altro tipo per questioni di sicurezza*/
    
    boolean setAdminTrue(ResponsibleUser user);
    
    boolean setAdminFalse(ResponsibleUser user);
    
    boolean changeEmail(ResponsibleUser user);
    
    List<ResponsibleUser> getResponsibleUsers();
}
