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
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import framework.data.proxy.PollProxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollweb.data.model.Partecipant;

/**
 *
 * @author davide
 */
public class PartecipantDAO_MySQL extends DAO implements PartecipantDAO{

    private PreparedStatement searchPartecipantById, searchPartecipantByPollId, searchPartecipantByApiKey;
    private PreparedStatement deletePartecipant;
    private PreparedStatement createFullPartecipant, createHalfPartecipant, getPartecipants,insertByToken;
    private PreparedStatement insertPartecipant;
    private PreparedStatement updatePartecipant;
    private PreparedStatement addPartecipantToPoll;
    private PreparedStatement addToken;
    private PreparedStatement deleteParticipantToPoll;
    private PreparedStatement checkPartecipant;
    public PartecipantDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        
        try {
            super.init();
            checkPartecipant = connection.prepareStatement("SELECT * FROM participant, participation WHERE participant.ID = participation.ID_part AND participant.email=? AND participant.pwd=? AND participation.ID_poll=? ");
            searchPartecipantById = connection.prepareStatement("SELECT * FROM participant WHERE ID=?");
            searchPartecipantByPollId = connection.prepareStatement("SELECT participant.id as id FROM participant, participation where participant.ID = participation.ID_part AND participation.ID_poll = ?");
            getPartecipants = connection.prepareStatement("SELECT ID FROM participant");
            searchPartecipantByApiKey = connection.prepareStatement("SELECT * FROM participant WHERE apiKey=?");
            deletePartecipant = connection.prepareStatement("DELETE FROM participant WHERE ID=? ");
            createFullPartecipant = connection.prepareStatement("INSERT INTO participant (apiKey, nameP, email, pwd) VALUES(?,?,?,?)");
            createHalfPartecipant = connection.prepareStatement("INSERT INTO participant (apiKey, nameP) VALUES(?,?)");
            insertPartecipant = connection.prepareStatement("INSERT INTO participant (email, pwd, nameP) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            addToken = connection.prepareStatement("UPDATE participant SET apiKey=? WHERE email=?");
            addPartecipantToPoll = connection.prepareStatement("INSERT INTO participation(id_poll, id_part) values (?,?)");
            updatePartecipant = connection.prepareStatement("UPDATE participant SET email=?, pwd=?, nameP=? where ID=?");
            deleteParticipantToPoll = connection.prepareStatement("DELETE FROM participation WHERE ID_part=? AND ID_poll=?");
            insertByToken = connection.prepareStatement("INSERT INTO participant (apiKey) VALUES (?)");
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
            insertByToken.close();
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

    @Override
    public void storePartecipant(Partecipant p, int pollId) throws DataException {
        int key = p.getKey();
        try {
            if (p.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (p instanceof PartecipantProxy && !((PartecipantProxy) p).isDirty()) {
                    return;
                }
                updatePartecipant.setString(1, p.getEmail());
                updatePartecipant.setString(2, p.getPwd());
                updatePartecipant.setString(3, p.getNameP());
                updatePartecipant.setInt(4, p.getKey());

                updatePartecipant.executeUpdate();
            } else { //insert
                insertPartecipant.setString(1, p.getEmail());
                insertPartecipant.setString(2, p.getPwd());
                insertPartecipant.setString(3, p.getNameP());
                if (insertPartecipant.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = insertPartecipant.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                    //aggiornaimo la chiave in caso di inserimento
                    //after an insert, uopdate the object key
                    p.setKey(key);
                    addPartecipantToPoll(p, pollId);

                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                article.copyFrom(getArticle(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (p instanceof PartecipantProxy) {
                ((PartecipantProxy) p).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }
    @Override
    public boolean addPartecipantToPoll(Partecipant p, int pollid) throws DataException{
        try{
            addPartecipantToPoll.setInt(1, pollid);
            addPartecipantToPoll.setInt(2, p.getKey());
            int result =  addPartecipantToPoll.executeUpdate();
            if (result == 1) {
                return true;
            }
        }
        catch (SQLException ex){
            throw new DataException("exception", ex);
        }
        return false;
    }



    @Override
    public boolean setToken(String mail, String token) throws DataException {
        try{
            this.addToken.setString(1, token);
            this.addToken.setString(2, mail);
            int result = this.addToken.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DataException("wwww", ex);
        }

        return false;
    }

    @Override
    public boolean deleteParticipantToPoll(Partecipant p, int pollid) throws DataException {
        try {
            this.deleteParticipantToPoll.setInt(1, p.getKey());
            this.deleteParticipantToPoll.setInt(2, pollid);
            this.deletePartecipant.setInt(1, p.getKey());
             this.deleteParticipantToPoll.executeUpdate();
            return this.deletePartecipant.executeUpdate() == 1;
        }
        catch (SQLException ex){
            throw new DataException("ex", ex);
        }
    }

    @Override //checkPartecipant = connection.prepareStatement("SELECT * FROM participant, participation WHERE participant.ID = participation.ID_part AND participant.email=? AND participant.pwd=? participation.ID_poll=? ");
    public boolean loginPartecipant(Partecipant p, int pollid) throws DataException {
        try {
            this.checkPartecipant.setString(1, p.getEmail());
            this.checkPartecipant.setString(2, p.getPwd());
            this.checkPartecipant.setInt(3, pollid);
            try (ResultSet rs = this.checkPartecipant.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataException("login partecipant error", e);
        }

    }

    @Override
    public void openPartecipant(String token) throws DataException {
        try {
            insertByToken.setString(1, token);
            insertByToken.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PartecipantDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
