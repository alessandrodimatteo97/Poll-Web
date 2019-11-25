/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

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
    
    Poll createReservedPoll( ResultSet rs );
    
    Poll createOpenPoll();
    
    Poll createOpenPoll( ResultSet rs );
    
    Poll getPollById(int pollId);
    
    List<Poll> getPollsByUserId(int userId);
    
    List<Poll> getOpenPolls();
    
    List<Poll> getReservedPolls();

    boolean setActivated(Poll poll);
    
    boolean setDeactivated(Poll poll);
    
}