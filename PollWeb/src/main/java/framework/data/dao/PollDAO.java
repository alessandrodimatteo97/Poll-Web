/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.Poll;

/**
 *
 * @author davide
 */
public interface PollDAO {
    
    Poll createPoll();
    
    Poll createReservedPoll();
    
    Poll createReservedPoll( ResultSet rs ) throws DataException;
    
    Poll createOpenPoll();
    
    Poll createOpenPoll( ResultSet rs ) throws DataException;
    
    Poll createHomePoll( ResultSet rs ) throws DataException;

    
    Poll getPollById(int pollId) throws DataException;
    
    List<Poll> getPollsByUserId(int userId) throws DataException;
    
    List<Poll> getOpenPolls() throws DataException;
    
    List<Poll> getReservedPolls() throws DataException;

    boolean setActivated(Poll poll) throws DataException;
    
    boolean setDeactivated(Poll poll) throws DataException;
        
    void storePoll( Poll poll) throws DataException;
    
      List<Poll> getAllPolls() throws DataException;
  
      
    
}
