/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

import framework.data.proxy.PartecipantProxy;
import pollweb.data.model.Partecipant;

import javax.xml.crypto.Data;

/**
 *
 * @author davide
 */
public interface PartecipantDAO {
    
    Partecipant createPartecipant();
    
    PartecipantProxy createPartecipant(ResultSet rs) throws DataException;
    
    
/*    boolean deletePartecipantUser(int UserKey);
        
    boolean changePassword(Partecipant user, String pwd); 
    
    boolean changeEmail(Partecipant user, String email);
    
    boolean changeApiKey(Partecipant user, String ApiKey);
    
    boolean changeName(Partecipant user, String name);
 */
    Partecipant getUserById(int userId) throws DataException;
    
    Partecipant getUserByApiKey(String apiKey) throws DataException;

    List<Partecipant> getPartecipants() throws DataException;
    
    List<Partecipant> getPartecipantsByPollId(int pollId) throws DataException;

    void storePartecipant(Partecipant p, int pollId) throws DataException;

    boolean setToken(String mail, String token) throws DataException;

    boolean addPartecipantToPoll(Partecipant p, int pollid) throws DataException;

    boolean deleteParticipantToPoll(Partecipant p, int pollid) throws DataException;

    boolean loginPartecipant(Partecipant p, int pollid) throws DataException ;
}
