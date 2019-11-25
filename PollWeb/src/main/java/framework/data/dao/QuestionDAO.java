/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import java.sql.ResultSet;
import java.util.List;
import javax.json.Json;
import pollweb.data.model.Question;

/**
 *
 * @author davide
 */
public interface QuestionDAO {
    
    Question createQuestion();
    
    Question createQuestion(ResultSet rs);
    
    boolean deleteQuestion(Question question);
    
    boolean setQuestionType(Question question, String type);
    
    Json setPossibleAnswers(Question question, Json possibleAnswers);
    
    String setTextQuestion(Question question, String textQuestion);
    
    String setNoteQuestion(Question question, String noteQuestion);

    boolean setObbligation(Question question, String obl);
    
    Question getQuestionById(int questionId);
    
    List<Question> getQuestionsByPollId(int pollId);
    
    List<Question> getQuestionsByUserId(int userId);

}
