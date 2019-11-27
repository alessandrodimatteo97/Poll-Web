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
import java.util.List;
import pollweb.data.model.Poll;

/**
 *
 * @author davide
 */
public class PollDAO_MySQL extends DAO implements PollDAO {
    
    private PreparedStatement searchPollByPollId, searchPollByUserId, searchOpenPolls, searchReservedPolls; /** Ricerche */
    private PreparedStatement insertReservedPoll, insertOpenPoll, updatePoll, deletePoll; /** insert update and delete polls*/
    private PreparedStatement setPollAsActive, setPollAsDeactive; /**settano le poll attive e disabilitate */
    
    public PollDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            /*   Qui scrivo gli statement precompilati   */
            searchPollByPollId = connection.prepareStatement("SELECT * FROM poll WHERE ID=?");
            searchPollByUserId = connection.prepareStatement("SELECT * FROM poll WHERE idR=?");
            searchOpenPolls = connection.prepareStatement("SELECT * FROM poll WHERE typeP='open'");
            searchReservedPolls = connection.prepareStatement("SELECT * FROM poll WHERE typeP='reserved'");
            insertReservedPoll = connection.prepareStatement("INSERT INTO poll (title,apertureText,closerText,typeP,url,activated,idR) VALUES(?,?,?,reserved,?,?,?)");
            insertOpenPoll = connection.prepareStatement("INSERT INTO poll (title,apertureText,closerText,typeP,url,activated,idR) VALUES(?,?,?,open,?,?,?)");
            updatePoll = connection.prepareStatement("UPDATE poll SET title=?,apertureText=?,closerText=?,typeP=?,url=?,activated=?,idR=? WHERE ID=?");
            deletePoll = connection.prepareStatement("DELETE FROM poll WHERE ID=?");
            setPollAsActive = connection.prepareStatement("UPDATE poll SET activated='1' WHERE ID=?");
            setPollAsDeactive = connection.prepareStatement("UPDATE poll SET activated='0' WHERE ID=?");

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
            insertReservedPoll.close();
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
    public PollProxy createArticle() {
        return new PollProxy(getDataLayer());
    }

    @Override
    public Poll createPoll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Poll createReservedPoll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PollProxy createReservedPoll(ResultSet rs) {
        PollProxy poll = createPoll();
        try {
            poll.setKey(rs.getInt("ID"));
            poll.setRespUser(rs.getInt("idR"));
            poll.setTitle(rs.getString("title"));
            poll.setApertureText(rs.getString("apertureText"));
            poll.setCloserText(rs.getInt("closerText"));
            poll.setType("reserved");
            poll.setUrl(rs.getString("url"));
            poll.setActivated(rs.getString("activated"));
            
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
    public Poll createOpenPoll(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Poll getPollById(int pollId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Poll> getPollsByUserId(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Poll> getOpenPolls() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Poll> getReservedPolls() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setActivated(Poll poll) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setDeactivated(Poll poll) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
