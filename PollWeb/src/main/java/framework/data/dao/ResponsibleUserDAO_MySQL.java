/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class ResponsibleUserDAO_MySQL extends DAO implements ResponsibleUserDAO{

    public ResponsibleUserDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public ResponsibleUser createResponsibleUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponsibleUser createResponsibleUser(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteResponsibleUser(int UserKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changePassword(ResponsibleUser user, String pwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAdminTrue(ResponsibleUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAdminFalse(ResponsibleUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeEmail(ResponsibleUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ResponsibleUser> getResponsibleUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
