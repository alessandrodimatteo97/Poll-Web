/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;
import java.sql.ResultSet;
import java.util.List;
//import pollweb.data.model.Participation;
/**
 *
 * @author davide
 */
public interface ParticipationDAO {
    
  //  Participation createParticipation();
    
  //  Participation createParticipation( ResultSet rs);
    
  //  List<Participation> getParticipationByUserId (int userId);
    
  //  List<Participation> getParticipationByPollId (int pollId);
    
    boolean deleteParticipationsByUserId(int userId); /** Utile solo se dobbiamo eliminare uno user e tutte le suo partecipazioni */
    
    boolean deleteParticipationsByPollId(int pollId); /** Utile solo se dobbiamo eliminare una poll e tutte le suo partecipazioni */

}
