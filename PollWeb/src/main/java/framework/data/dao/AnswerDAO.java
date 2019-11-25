/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;
import java.util.List;
import pollweb.data.model.Answer;
import java.sql.ResultSet;

/**
 *
 * @author davide
 */
public interface AnswerDAO {
    
    Answer createAnswer();
    
    Answer createAnswer( ResultSet rs );
        
    List<Answer> getAnswersByQuestionId(int QuestionId);
    
    Answer getAnswerById(int AnswerId);
    
}
