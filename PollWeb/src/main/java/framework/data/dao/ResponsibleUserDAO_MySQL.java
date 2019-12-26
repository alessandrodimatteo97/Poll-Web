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
import java.sql.Statement;
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
    private PreparedStatement getAllResponsible, getResponsibleById, getResponsibleByToken, getAllRespNotAccepted;
    private PreparedStatement updateRespToAccepted;
    private PreparedStatement checkUserExist, checkAdmin;
    private PreparedStatement getResponsible;
    private PreparedStatement setToken;



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
            checkUserExist = connection.prepareStatement("SELECT * FROM responsibleUser WHERE email=? and pwd=?");

            insertResponsibleUser = connection.prepareStatement("INSERT INTO responsibleUser (nameR , surnameR, fiscalCode , email, pwd) values (?,?,?,?,?)" , Statement.RETURN_GENERATED_KEYS);
            updateResponsibleUser = connection.prepareStatement("UPDATE responsibleUser SET nameR=?, surnameR=?, email=?,pwd=?,administrator=?, accepted=?");
            setToken = connection.prepareStatement("UPDATE responsibleUser SET token=? WHERE email=?");
            getResponsible = connection.prepareStatement("SELECT * FROM responsibleUser where ID = ?");
            getResponsibleByToken = connection.prepareStatement("SELECT * FROM responsibleUser where token=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing poll data layer",ex);
        }
    }
    
    @Override 
    public void destroy() throws DataException {
        try {
            insertResponsibleUser.close();
            updateResponsibleUser.close();
            getResponsible.close();
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
    public boolean setAccepted(ResponsibleUser user) throws DataException {
        try{
            this.updateResponsibleUser.setString(1, user.getNameR());//nameR=?, surnameR=?, email=?,pwd=?,administrator=?, accepted=?
            this.updateResponsibleUser.setString(2, user.getSurnameR());
            this.updateResponsibleUser.setString(3, user.getEmail());
            this.updateResponsibleUser.setString(4, user.getPwd());
            this.updateResponsibleUser.setInt(5, user.getKey());

            int result = this.updateResponsibleUser.executeUpdate();
            if (result == 1) {
            return true;
        }
        } catch (SQLException ex) {
            throw new DataException("wwww", ex);
        }
        
        return false;
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
    public boolean checkAdmin(ResponsibleUser user) throws DataException {
        try {
            this.checkAdmin.setString(1, user.getEmail());
            this.checkAdmin.setString(2, user.getPwd());

            try (ResultSet rs = this.checkAdmin.executeQuery()) {
                if(rs.next()) return true;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to check admin", ex);
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

    @Override
    public boolean setToken(String mail, String token) throws DataException {
        try{
            this.setToken.setString(1, token);
            this.setToken.setString(2, mail);
            int result = this.setToken.executeUpdate();
            if (result == 1) {
            return true;
        }
        } catch (SQLException ex) {
            throw new DataException("wwww", ex);
        }

        return false;    }

    @Override
    public ResponsibleUser getResponsibleUser(String token) throws DataException {
        try {
            this.getResponsibleByToken.setString(1, token);

            try ( ResultSet rs = this.getResponsibleByToken.executeQuery() ) {
                if (rs.next()) {
                    return createResponsibleUser(rs);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }

        return null;
    }




}
