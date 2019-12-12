/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.proxy.ResponsibleUserProxy;
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
    
    private PreparedStatement insertResponsibleUser, updateResponsibleUser;

       private PreparedStatement checkUserExist;

    public ResponsibleUserDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try {
            super.init();
            checkUserExist = connection.prepareStatement("SELECT * FROM responsibleUser WHERE email=? and pwd=?");

            insertResponsibleUser = connection.prepareStatement("INSERT INTO responsibleUser (nameR , surnameR, fiscalCode , email, pwd) values (?,?,?,?,?)" , Statement.RETURN_GENERATED_KEYS);
            updateResponsibleUser = connection.prepareStatement("UPDATE responsibleUser SET nameR=?, surnameR=?, email=?,pwd=?,administrator=?, accepted=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing poll data layer",ex);
        }
    }
    
    @Override 
    public void destroy() throws DataException {
        try {
            insertResponsibleUser.close();
            updateResponsibleUser.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    
       @Override
    public ResponsibleUserProxy createResponsibleUser() {
        return new ResponsibleUserProxy(getDataLayer());
    }
    
 

    @Override
    public ResponsibleUserProxy createResponsibleUser(ResultSet rs) throws DataException {
        ResponsibleUserProxy ru = createResponsibleUser();
        
        try {
            ru.setKey(rs.getInt("ID"));
            ru.setNameR(rs.getString("nameR"));
            ru.setSurnameR(rs.getString("surnameR"));
            ru.setFiscalCode(rs.getString("fiscalCode"));
            ru.setEmail(rs.getString("email"));
            ru.setPwd(rs.getString("pwd"));
            ru.setAccepted(rs.getBoolean("accepted"));
            ru.setAdministrator(rs.getBoolean("administrator"));
          
            
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return ru; 
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
            throw new DataException("Unable find user", ex);
        }
        return false;
    }
    
    @Override
    public void storeResponsibleUser(ResponsibleUser responsibleUser) throws DataException {
        int key = responsibleUser.getKey();
        try {
            if (responsibleUser.getKey() > 0) {
                if (responsibleUser instanceof ResponsibleUserProxy && !((ResponsibleUserProxy) responsibleUser).isDirty()){
                    return;
                }
                updateResponsibleUser.setString(1, "nameR");
                updateResponsibleUser.setString(2,"surnameR");
                updateResponsibleUser.setString(3,"email");
                updateResponsibleUser.setString(4, "pwd");
                updateResponsibleUser.setString(5,"administrator");
                updateResponsibleUser.setString(6, "accepted");
                
                
            }else {
                insertResponsibleUser.setString(1, responsibleUser.getNameR());
                insertResponsibleUser.setString(2, responsibleUser.getSurnameR());
                insertResponsibleUser.setString(3, responsibleUser.getFiscalCode());
                insertResponsibleUser.setString(4, responsibleUser.getEmail());
                insertResponsibleUser.setString(5, responsibleUser.getPwd());
            }
            
            if (insertResponsibleUser.executeUpdate() == 1){
                try(ResultSet keys = insertResponsibleUser.getGeneratedKeys()){
                    if(keys.next()){
                        key = keys.getInt(1);
                    }
                }
                responsibleUser.setKey(key);
            } if (responsibleUser instanceof ResponsibleUserProxy){
                ((ResponsibleUserProxy) responsibleUser).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store responsible", ex);
        }
    }

    
}
