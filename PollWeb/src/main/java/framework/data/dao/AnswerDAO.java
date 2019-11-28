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

/**
 *
 * @author davide
 */
public interface AnswerDAO {
    
    Answer createAnswer();
    
    Answer createAnswer( ResultSet rs ) throws DataException;
        
    List<Answer> getAnswersByQuestionId(int QuestionId) throws DataException;
    
    Answer getAnswerById(int AnswerId) throws DataException;
    
}
