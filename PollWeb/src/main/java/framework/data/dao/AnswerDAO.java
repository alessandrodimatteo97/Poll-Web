/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;
import framework.data.DataException;
import java.util.List;
import pollweb.data.model.Answer;
import java.sql.ResultSet;
import pollweb.data.model.Question;

/**
 *
 * @author davide
 */
public interface AnswerDAO {
    
    Answer createAnswer();
    
    Answer createAnswer( ResultSet rs ) throws DataException;
        
    List<Answer> getAnswersByQuestionId(int questionKey) throws DataException; //cambiato la firma del metodo 
    
    Answer getAnswerById(int AnswerId) throws DataException;

    Answer getAnswerByQuestionIdParticipantId(int idp, int idq)throws DataException;
}
