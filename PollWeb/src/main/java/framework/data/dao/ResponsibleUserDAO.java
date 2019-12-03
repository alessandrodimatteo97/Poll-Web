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
public interface ResponsibleUserDAO {
       
    ResponsibleUser createResponsibleUser();
    
    ResponsibleUser createResponsibleUser(ResultSet rs) throws DataException;
    
    boolean deleteResponsibleUser(int UserKey) throws DataException;
        
    boolean changePassword(ResponsibleUser user, String pwd) throws DataException; /* controllare la password se deve essere di un altro tipo per questioni di sicurezza*/
    
    boolean setAdminTrue(ResponsibleUser user) throws DataException;
    
    boolean setAdminFalse(ResponsibleUser user) throws DataException;
    
    boolean changeEmail(ResponsibleUser user) throws DataException;
    
    List<ResponsibleUser> getResponsibleUsers() throws DataException;
    
    ResponsibleUser getResponsibleUser (int UserKey) throws DataException;
    
    boolean checkResponsible (ResponsibleUser user) throws DataException;
}
