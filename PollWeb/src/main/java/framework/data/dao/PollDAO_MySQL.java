/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.proxy.PollProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollweb.data.model.Poll;

/**
 *
 * @author davide
 */
public class PollDAO_MySQL extends DAO implements PollDAO {
    private PreparedStatement getAllPolls;
    private PreparedStatement searchPollByPollId, searchPollByUserId, searchOpenPolls, searchReservedPolls, searchAlreadyActivatedPollsByUserId; /** Ricerche */
    private PreparedStatement insertPoll, insertOpenPoll, updatePoll, deletePoll; /** insert update and delete polls*/
    private PreparedStatement setPollAsActive, setPollAsDeactive; /**settano le poll attive e disabilitate */
    private PreparedStatement setPollAlreadyActivated;

    private PreparedStatement pollContainsQuestion; // controlla se la question appartiene al poll
    public PollDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            /*   Qui scrivo gli statement precompilati   */
            getAllPolls = connection.prepareStatement("SELECT * FROM poll");
            searchPollByPollId = connection.prepareStatement("SELECT * FROM poll WHERE ID=?");
            searchPollByUserId = connection.prepareStatement("SELECT * FROM poll WHERE idR=? AND activated='no' AND alreadyActivated='no' OR activated='yes' AND alreadyActivated='yes'");
            searchAlreadyActivatedPollsByUserId = connection.prepareStatement("SELECT * FROM poll WHERE idR=? AND activated='no' AND alreadyActivated='yes'");
            searchOpenPolls = connection.prepareStatement("SELECT * FROM poll WHERE typeP='open'");
            searchReservedPolls = connection.prepareStatement("SELECT * FROM poll WHERE typeP='reserved'");
            insertPoll = connection.prepareStatement("INSERT INTO poll (title,apertureText,closerText,typeP,url,activated,idR) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertOpenPoll = connection.prepareStatement("INSERT INTO poll (title,apertureText,closerText,typeP,url,activated,idR) VALUES(?,?,?,open,?,?,?)");
            updatePoll = connection.prepareStatement("UPDATE poll SET title=?,apertureText=?,closerText=?,typeP=?,url=?,activated=?,idR=? WHERE ID=?");
            deletePoll = connection.prepareStatement("DELETE FROM poll WHERE ID=?");
            setPollAsActive = connection.prepareStatement("UPDATE poll SET activated='yes' WHERE ID=?");
            setPollAsDeactive = connection.prepareStatement("UPDATE poll SET activated='no' WHERE ID=?");
            setPollAlreadyActivated = connection.prepareStatement("UPDATE poll SET alreadyActivated='yes' WHERE ID=?");

            setPollAsActive = connection.prepareStatement("UPDATE poll SET activated='1' WHERE ID=?");
            setPollAsDeactive = connection.prepareStatement("UPDATE poll SET activated='0' WHERE ID=?");
            pollContainsQuestion = connection.prepareStatement("SELECT * FROM PollWeb.question where IDP = ? AND ID = ?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing poll data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {
            searchPollByPollId.close();
            searchPollByUserId.close();
            searchOpenPolls.close();
            searchReservedPolls.close();
            insertPoll.close();
            insertOpenPoll.close();
            updatePoll.close();
            deletePoll.close();
            setPollAsActive.close();
            setPollAsDeactive.close();
        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
    }
    
    @Override
    public PollProxy createPoll() {
        return new PollProxy(getDataLayer());
    }

    @Override
    public PollProxy createPoll(ResultSet rs) throws DataException {
        PollProxy poll = createPoll();

        try {
            poll.setKey(rs.getInt("ID"));
            poll.setRespUserKey(rs.getInt("idR"));
            poll.setTitle(rs.getString("title"));
            poll.setApertureText(rs.getString("apertureText"));
            poll.setCloserText(rs.getString("closerText"));
            poll.setType(rs.getString("typeP"));
            poll.setUrl(rs.getString("url"));
            poll.setActivated(true);

        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return poll;
    }

    @Override
    public Poll createReservedPoll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PollProxy createReservedPoll(ResultSet rs) throws DataException {
        PollProxy poll = createPoll();
        
        try {
            poll.setKey(rs.getInt("ID"));
            poll.setRespUserKey(rs.getInt("idR"));
            poll.setTitle(rs.getString("title"));
            poll.setApertureText(rs.getString("apertureText"));
            poll.setCloserText(rs.getString("closerText"));
            poll.setType("reserved");
            poll.setUrl(rs.getString("url"));
            poll.setActivated(rs.getString("activated"));
            poll.setAlreadyActivated(rs.getString("alreadyActivated"));

        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return poll;
    }

    @Override
    public Poll createOpenPoll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PollProxy createOpenPoll(ResultSet rs) throws DataException {
        PollProxy poll = createPoll();
        
        try {
            poll.setKey(rs.getInt("ID"));
            poll.setRespUserKey(rs.getInt("idR"));
            poll.setTitle(rs.getString("title"));
            poll.setApertureText(rs.getString("apertureText"));
            poll.setCloserText(rs.getString("closerText"));
            poll.setType("open");
            poll.setUrl(rs.getString("url"));
            poll.setActivated(rs.getString("activated"));
            poll.setAlreadyActivated(rs.getString("alreadyActivated"));
            
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return poll;    
    }
@Override
    public List<Poll> getAllPolls() throws DataException{
        
        List<Poll> result = new ArrayList();

            try (ResultSet rs = getAllPolls.executeQuery()) {
               while(rs.next()) {
                       result.add((Poll)createHomePoll(rs));
               }             
        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return result;
    }
    @Override
    public Poll getPollById(int pollId) throws DataException {
        try {
            searchPollByPollId.setInt(1, pollId);
            
            try ( ResultSet rs = searchPollByPollId.executeQuery() ) {
                if (rs.next()) {
                 //   if(rs.getString("typeP").equals("open")) {
                      //  return createOpenPoll(rs);
                  //  }
                   // else {
                        return createPoll(rs);
                   // }
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return null;
    }

    @Override
    public List<Poll> getPollsByUserId(int userId) throws DataException {
        
        List<Poll> result = new ArrayList();

        try {
            
            this.searchPollByUserId.setInt(1, userId);
           
            try (ResultSet rs = searchPollByUserId.executeQuery()) {
               while(rs.next()) {
                   if(rs.getString("typeP").equals("open")) {
                       result.add((Poll)getPollById(rs.getInt("ID")));
                   } else {
                       result.add((Poll)getPollById(rs.getInt("ID")));
                   }
               } 
            } 
            
        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }

        return result;
    }

    @Override
    public List<Poll> getPollsAlreadyActivatedAndClosedByUserId(int userId) throws DataException {

        List<Poll> result = new ArrayList();

        try {

            this.searchAlreadyActivatedPollsByUserId.setInt(1, userId);

            try (ResultSet rs = searchAlreadyActivatedPollsByUserId.executeQuery()) {
                while(rs.next()) {
                    if(rs.getString("typeP").equals("open")) {
                        result.add((Poll)getPollById(rs.getInt("ID")));
                    } else {
                        result.add((Poll)getPollById(rs.getInt("ID")));
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }

        return result;
    }

    @Override

    public List<Poll> getOpenPolls() throws DataException {
        List<Poll> result = new ArrayList();
        
        try (ResultSet rs = searchOpenPolls.executeQuery()) {
            while (rs.next()) {
                try {
                    result.add((Poll) getPollById(rs.getInt("ID")));
                } catch (DataException ex) {
                    Logger.getLogger(PollDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PollDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return result;
    }

    @Override
    public List<Poll> getReservedPolls() throws DataException {
        List<Poll> result = new ArrayList();
        try ( ResultSet rs = searchReservedPolls.executeQuery()) {
            while(rs.next()) {
                result.add((Poll) getPollById(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return result;
    }

    @Override
    public boolean setActivated( Poll poll)  throws DataException {

        try {
            setPollAsActive.setInt(1, poll.getKey());
        } catch (SQLException ex) {
            throw new DataException("Not setted property:  ", ex);
        }
        
        try {
            int result = setPollAsActive.executeUpdate();
            if (result == 1) {
                setAlreadyActivated(poll);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new DataException("Non è andata bene pollDAO:  ", ex);
        }

    }

    @Override
    public boolean setAlreadyActivated(Poll poll) throws DataException {
        try {
            setPollAlreadyActivated.setInt(1, poll.getKey());
        } catch (SQLException ex) {
            throw new DataException("Not setted property:  ", ex);
        }

        try {
            int result = setPollAlreadyActivated.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new DataException("Non è andata bene pollDAO:  ", ex);
        }
                
    }

    @Override
    public boolean setDeactivated( Poll poll ) throws DataException {
        try {
            setPollAsDeactive.setInt(1, poll.getKey());
        } catch (SQLException ex) {
            throw new DataException("Not setted property:  ", ex);
        }

        try {
            int result = setPollAsDeactive.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new DataException("Non è andata bene pollDAO:  ", ex);
        }
    }

    @Override    //title=?,apertureText=?,closerText=?,typeP=?,url=?,activated=?,idR=? WHERE ID=?
    public void storePoll(Poll poll) throws DataException {
        int key = poll.getKey();
        try {
            if (poll.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (poll instanceof PollProxy && !((PollProxy) poll).isDirty()) {
                    return;
                }
                updatePoll.setString(1, poll.getTitle());
                updatePoll.setString(2, poll.getApertureText());
                updatePoll.setString(3, poll.getCloserText());
                updatePoll.setString(4, poll.getType());
                updatePoll.setString(5, poll.getUrl());
                updatePoll.setInt(6, 1);
                if (poll.getRespUser()!= null) {
                    updatePoll.setInt(7, poll.getRespUser().getKey());
                } else {
                    updatePoll.setNull(7, java.sql.Types.INTEGER);
                }
                updatePoll.setInt(8, poll.getKey());
              
                updatePoll.executeUpdate();
            } else { //insert
                insertPoll.setString(1, poll.getTitle());
                insertPoll.setString(2, poll.getApertureText());
                insertPoll.setString(3, poll.getCloserText());
                insertPoll.setString(4, poll.getType());
                insertPoll.setString(5, poll.getUrl());
                insertPoll.setInt(6, 1);
                if (poll.getRespUser()!= null) {
                    insertPoll.setInt(7, poll.getRespUser().getKey());
                } else {
                    insertPoll.setNull(7, java.sql.Types.INTEGER);
                }

                if (insertPoll.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = insertPoll.getGeneratedKeys()) {
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
                    poll.setKey(key);
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
            if (poll instanceof PollProxy) {
                ((PollProxy) poll).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }

    @Override
    public Poll createHomePoll(ResultSet rs) throws DataException {
            PollProxy poll = createPoll();
        
        try {
            poll.setKey(rs.getInt("ID"));
            poll.setRespUserKey(rs.getInt("idR"));
            poll.setTitle(rs.getString("title"));
            poll.setApertureText(rs.getString("apertureText"));
            poll.setCloserText(rs.getString("closerText"));
            if(rs.getString("typeP").equals("open")){
              poll.setType("open");

            }
            else {
              poll.setType("reserved");

            }
            poll.setUrl(rs.getString("url"));
            poll.setActivated("yes");
            
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return poll;    }
    

    @Override
    public boolean pollContainsQuestion(int poll_key, int question_key) throws DataException {
        try {
            this.pollContainsQuestion.setInt(1, poll_key);
            this.pollContainsQuestion.setInt(2, question_key);
          ResultSet rs =   this.pollContainsQuestion.executeQuery();
          if(rs.next()){
              return true;
          }
          return false;
        } catch (SQLException ex) {
            throw new DataException("Unable to search question by poll", ex);
        }
    }

}
