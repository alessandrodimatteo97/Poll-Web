/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

import framework.data.proxy.PollProxy;
import pollweb.data.model.Poll;

/**
 *
 * @author davide
 */
public interface PollDAO {
    
    Poll createPoll();

    PollProxy createPoll(ResultSet rs) throws DataException;


    Poll createReservedPoll() throws DataException;
    
    Poll createReservedPoll( ResultSet rs ) throws DataException;
    
    Poll createOpenPoll() throws DataException;
    
    Poll createOpenPoll( ResultSet rs ) throws DataException;
    
    Poll createHomePoll( ResultSet rs ) throws DataException;

    
    Poll getPollById(int pollId) throws DataException;
    
    List<Poll> getPollsByUserId(int userId) throws DataException;
    
    List<Poll> getOpenPolls() throws DataException;
    
    List<Poll> getReservedPolls() throws DataException;

    boolean setActivated(ResultSet rs) throws DataException;
    
    boolean setDeactivated(ResultSet rs) throws DataException;
        
    void storePoll( Poll poll) throws DataException;
    
      List<Poll> getAllPolls() throws DataException;
  
     boolean pollContainsQuestion(int poll_key, int question_key) throws DataException;
      
    
}
