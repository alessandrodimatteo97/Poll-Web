/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
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
    
    Question createQuestion(ResultSet rs) throws DataException;
    
    boolean deleteQuestion(Question question) throws DataException;
    
    boolean setQuestionType(Question question, String type) throws DataException;
    
    Json setPossibleAnswers(Question question, Json possibleAnswers) throws DataException;
    
    String setTextQuestion(Question question, String textQuestion) throws DataException;
    
    String setNoteQuestion(Question question, String noteQuestion) throws DataException;

    boolean setObbligation(Question question, String obl) throws DataException;
    
    Question getQuestionById(int questionId) throws DataException;
    
    List<Question> getQuestionsByPollId(int pollId) throws DataException;
    
    List<Question> getQuestionsByUserId(int userId) throws DataException;

}
