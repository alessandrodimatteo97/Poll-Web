/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class ResponsibleUserDAO_MySQL extends DAO implements ResponsibleUserDAO{

        private PreparedStatement checkUserExist;

    public ResponsibleUserDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

       
            checkUserExist = connection.prepareStatement("SELECT * FROM responsibleUser WHERE email=? and pwd=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }


    @Override
    public ResponsibleUser createResponsibleUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponsibleUser createResponsibleUser(ResultSet rs) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteResponsibleUser(int UserKey) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changePassword(ResponsibleUser user, String pwd) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAdminTrue(ResponsibleUser user) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAdminFalse(ResponsibleUser user) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeEmail(ResponsibleUser user) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ResponsibleUser> getResponsibleUsers() throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponsibleUser getResponsibleUser(int UserKey) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkResponsible(ResponsibleUser user) throws DataException {
		  try{
                    this.checkUserExist.setString(1, user.getEmail());
                    this.checkUserExist.setString(2, user.getPwd());
            try (ResultSet rs = this.checkUserExist.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return false;
    }
    
    
}
