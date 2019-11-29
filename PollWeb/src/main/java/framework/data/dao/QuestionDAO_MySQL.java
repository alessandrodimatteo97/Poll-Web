/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.proxy.QuestionProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.json.Json;
import pollweb.data.model.Answer;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;

/**
 *
 * @author davide
 */
public class QuestionDAO_MySQL extends DAO implements QuestionDAO {
    
    private PreparedStatement createQuestion;
    private PreparedStatement getQuestionsByPollId, getQuestionById;
    private PreparedStatement updateQuestion, deleteQuestion;
    
    public QuestionDAO_MySQL(DataLayer d) {
        super(d);
    }

     @Override
    public void init() throws DataException {
        try {
            super.init();
            
             getQuestionById = connection.prepareStatement("SELECT * FROM question WHERE ID=?");
             getQuestionsByPollId = connection.prepareStatement("SELECT * FROM question JOIN poll ON question.IDP=poll.ID WHERE poll.ID=?");
             updateQuestion = connection.prepareStatement("SELECT * FROM poll WHERE typeP='open'");
             deleteQuestion = connection.prepareStatement("DELETE FROM question WHERE ID=?");
             createQuestion = connection.prepareStatement("INSERT INTO question (typeq,textq,note,obbligation,possible_answer,IDP) VALUES(?,?,?,?,?,?)");
        } catch (SQLException ex) {
            throw new DataException("Error initializing poll data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {
            getQuestionById.close();
            getQuestionsByPollId.close();
            updateQuestion.close();
            deleteQuestion.close();
            createQuestion.close();

        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
    }
    @Override
    public QuestionProxy createQuestion() {
        return new QuestionProxy(getDataLayer());
    }

    @Override
    public QuestionProxy createQuestion(ResultSet rs) throws DataException {
        QuestionProxy question = createQuestion();
        
        try {
            question.setKey(rs.getInt("ID"));
            question.setNote(rs.getString("note"));
            question.setAnswer((List<Answer>) rs.getArray("possible_answer")); //non funziona secondo me
            if(rs.getString("obbligation").equals("yes")){
                question.setObbligated(true);
            } else {
                question.setObbligated(false);
            }
            question.setTypeP(rs.getString("typeq"));
            question.setTextq(rs.getString("textq"));
            question.setPollKey(rs.getInt("IDP"));
        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
        
        return question;
    }

    @Override
    public boolean deleteQuestion(ResultSet rs) throws DataException {
        try{
            this.deleteQuestion.setInt(1, rs.getInt("ID"));
            try(ResultSet result = this.deleteQuestion.executeQuery()) {
                return true;
            } 
        } catch (SQLException ex) {
                return false;
        }
    }

    @Override
    public boolean setQuestionType(Question question, String type) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Json setPossibleAnswers(Question question, Json possibleAnswers) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setTextQuestion(Question question, String textQuestion) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setNoteQuestion(Question question, String noteQuestion) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setObbligation(Question question, String obl) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Question getQuestionById(int questionId) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> getQuestionsByPollId(Poll poll) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> getQuestionsByUserId(int userId) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
