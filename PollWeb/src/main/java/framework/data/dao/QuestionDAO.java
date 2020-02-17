/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import java.sql.ResultSet;
import java.util.List;

import framework.data.proxy.QuestionProxy;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;
import org.json.JSONObject;
/**
 *
 * @author davide
 */
public interface QuestionDAO {
    
    Question createQuestion();

    QuestionProxy createQuestion(ResultSet rs) throws DataException;
    
    boolean deleteQuestion(int question_key) throws DataException;
    
    boolean setQuestionType(Question question, String type) throws DataException;
    
    JSONObject setPossibleAnswers(Question question, JSONObject possibleAnswers) throws DataException;
    
    String setTextQuestion(Question question, String textQuestion) throws DataException;
    
    String setNoteQuestion(Question question, String noteQuestion) throws DataException;

    boolean setObbligation(Question question, String obl) throws DataException;
    
    Question getQuestionById(ResultSet rs) throws DataException;

    Question getQuestionById(int keyQ) throws DataException;

    List<Question> getQuestionsByPollId(int keyPoll) throws DataException; //cambiata la firma del metodo
    
    List<Question> getQuestionsByUserId(int userId) throws DataException;
    
    int getQuestionNumber(int poll_ket) throws DataException;

    void store(Question question) throws DataException;

    boolean checkQuestionPoll(int poll_key, int question_key) throws DataException;
    
    boolean checkQuestionUser(int question_key, String token) throws DataException;
}
