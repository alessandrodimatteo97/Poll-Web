/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.proxy.PartecipantProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import pollweb.data.model.Partecipant;

/**
 *
 * @author davide
 */
public class PartecipantDAO_MySQL extends DAO implements PartecipantDAO{

    private PreparedStatement searchPartecipantById, searchPartecipantByPollId, searchPartecipantByApiKey;
    private PreparedStatement deletePartecipant;
    private PreparedStatement createFullPartecipant, createHalfPartecipant, getPartecipants;
    
    public PartecipantDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        
        try {
            super.init();
            
            searchPartecipantById = connection.prepareStatement("SELECT * FROM participant WHERE ID=?");
            searchPartecipantByPollId = connection.prepareStatement("SELECT * FROM participant JOIN partecipation ON participant.ID=partecipation.ID_part "
                    + "WHERE partecipation.ID_poll=?");
            getPartecipants = connection.prepareStatement("SELECT ID FROM participant");
            searchPartecipantByApiKey = connection.prepareStatement("SELECT * FROM participant WHERE apiKey=?");
            deletePartecipant = connection.prepareStatement("DELETE FROM participant WHERE ID=? ");
            createFullPartecipant = connection.prepareStatement("INSERT INTO participant (apiKey, nameP, email, pwd) VALUES(?,?,?,?)");
            createHalfPartecipant = connection.prepareStatement("INSERT INTO participant (apiKey, nameP) VALUES(?,?)");

        } catch (SQLException ex) {
            throw new DataException("Error initializing partecipant data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {
            
            searchPartecipantById.close();
            searchPartecipantByPollId.close();
            searchPartecipantByApiKey.close();
            deletePartecipant.close();
            createFullPartecipant.close();
            createHalfPartecipant.close();
        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
    }
    
    @Override
    public PartecipantProxy createPartecipant() {
        return new PartecipantProxy(getDataLayer());
    }


    @Override
    public PartecipantProxy createPartecipant(ResultSet rs) throws DataException{
        PartecipantProxy partecipant = createPartecipant();
        try{
            partecipant.setApiKey(rs.getString("apiKey"));
            partecipant.setEmail(rs.getString("email"));
            partecipant.setNameP(rs.getString("nameP"));
            partecipant.setPwd(rs.getString("pwd"));
            partecipant.setKey(rs.getInt("ID"));
        } catch (SQLException ex) {
            
        }
        return partecipant;
    }

    @Override
    public List<Partecipant> getPartecipants() throws DataException{
        List<Partecipant> result = new ArrayList();
        try( ResultSet rs = this.getPartecipants.executeQuery()) {
            while(rs.next()) {
                result.add((Partecipant) getUserById(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException ("Error from db" + ex);
        }
        return result;
    }

    @Override
    public Partecipant getUserById(int userId) throws DataException {
        try {
            this.searchPartecipantById.setInt(1, userId);
            
            try ( ResultSet rs = this.searchPartecipantById.executeQuery() ) {
                if (rs.next()) {
                    return createPartecipant(rs);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return null;
    }

    @Override
    public List<Partecipant> getPartecipantsByPollId(int pollId) throws DataException {
        List<Partecipant> result = new ArrayList();
        try {
            this.searchPartecipantByPollId.setInt(1, pollId);
            try ( ResultSet rs = this.searchPartecipantByPollId.executeQuery() ) {
                while (rs.next()) {
                    result.add((Partecipant) getUserById(rs.getInt("ID")));
                }
            } 
            } catch (SQLException ex) {
                throw new DataException("Error from DataBase: ", ex);
        }
        return result;
    }

    @Override
    public Partecipant getUserByApiKey(String apiKey) throws DataException {
        try {
            this.searchPartecipantByApiKey.setString(1, apiKey);
            
            try ( ResultSet rs = this.searchPartecipantByApiKey.executeQuery()) {
                if( rs.next()) {
                    return createPartecipant(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error from database: ", ex);
        }
        return null;
    }
    
}
