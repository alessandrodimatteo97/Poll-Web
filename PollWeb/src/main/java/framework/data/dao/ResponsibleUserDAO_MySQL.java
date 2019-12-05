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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class ResponsibleUserDAO_MySQL extends DAO implements ResponsibleUserDAO{
    
    private PreparedStatement insertResponsibleUser, updateResponsibleUser;
    private PreparedStatement getAllResponsible, getResponsibleById, getAllRespNotAccepted;
    private PreparedStatement updateRespToAccepted;
    
    public ResponsibleUserDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try {
            super.init();
            
            insertResponsibleUser = connection.prepareStatement("INSERT INTO responsibleUser (nameR , surnameR, fiscalCode , email, pwd) values (?,?,?,?,?)");
            //updateResponsibleUser = connection.prepareStatement("UPDATE responsibleUser SET nameR=?, surnameR=?, email=?,pwd=?,administrator=?, accepted=?");
            getAllResponsible = connection.prepareStatement("SELECT ID FROM responsibleUser");
            getResponsibleById = connection.prepareStatement("SELECT * FROM responsibleUser WHERE ID=?");
            getAllRespNotAccepted = connection.prepareCall("SELECT ID FROM responsibleUser WHERE accepted=0");
            updateRespToAccepted = connection.prepareStatement("UPDATE responsibleUser SET accepted=? WHERE ID=?");
            
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
            ru.setAccepted(rs.getInt("accepted"));
            ru.setAdministrator(rs.getString("administrator"));
          
            
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
    public ResponsibleUser getResponsibleUser(int UserKey) throws DataException {
        try {
            this.getResponsibleById.setInt(1, UserKey);
            
            try ( ResultSet rs = this.getResponsibleById.executeQuery() ) {
                if (rs.next()) {
                    return createResponsibleUser(rs);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return null;
    }
    @Override
    public List<ResponsibleUser> getResponsibleUsers() throws DataException{
        List<ResponsibleUser> result = new ArrayList();
        try( ResultSet rs = this.getAllResponsible.executeQuery()) {
            while(rs.next()) {
                result.add((ResponsibleUser) getResponsibleUser(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException ("Error from db" + ex);
        }
        return result;
    }
    
    @Override
    public List<ResponsibleUser> getResponsibleUsersNotAccepted() throws DataException{
        List<ResponsibleUser> result = new ArrayList();
        try( ResultSet rs = this.getAllRespNotAccepted.executeQuery()) {
            while(rs.next()) {
                result.add((ResponsibleUser) getResponsibleUser(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException ("Error from db" + ex);
        }
        return result;
    }
    @Override
    public void setAccepted(int userKey) throws DataException {
        try {
            this.updateRespToAccepted.setInt(1, 1);
            this.updateRespToAccepted.setInt(2, userKey);
            
            ResultSet rs ;
            this.getResponsibleById.executeUpdate();

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
    }
    
/*
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
*/

    
}
