/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.proxy.AnswerProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import pollweb.controller.InsertParticipant;
import pollweb.data.model.Answer;
import pollweb.data.model.Question;

/**
 *
 * @author davide
 */
public class AnswerDAO_MySQL extends DAO implements AnswerDAO {

    private PreparedStatement searchAnswerById;
    private PreparedStatement getAnswersByQuestionId, getAnswersByUserId; 
    private PreparedStatement insertAnswer;
    private PreparedStatement getAnswerbyIdParticipantQuestionId;
    public AnswerDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            searchAnswerById = connection.prepareStatement("SELECT * FROM answer WHERE ID=?");
            getAnswersByQuestionId = connection.prepareStatement("SELECT * FROM answer JOIN question ON answer.IDQ=question.ID WHERE question.ID=?");
            getAnswersByUserId = connection.prepareStatement("SELECT * FROM answer WHERE ID_P=?");
            insertAnswer = connection.prepareStatement("INSERT INTO answer (IDQ,ID_P,texta) VALUES(?,?,?)");
            getAnswerbyIdParticipantQuestionId = connection.prepareStatement("SELECT * FROM answer where ID_P=? AND IDQ=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing answer data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {
            searchAnswerById.close();
            getAnswersByQuestionId.close();
            getAnswersByUserId.close();
            insertAnswer.close();
        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
    }

    @Override
    public AnswerProxy createAnswer() {
        return new AnswerProxy(getDataLayer());
    }

    @Override
    public Answer createAnswer(ResultSet rs) throws DataException {
        AnswerProxy answer = createAnswer();
        
        try {
            answer.setKey(rs.getInt("ID"));
            if (rs.getString("texta") != null){
               JSONObject j = new JSONObject(rs.getString("texta"));
                answer.setTextA(j);

            }

            answer.setPartecipantKey(rs.getInt("ID_P"));
            answer.setQuestionKey(rs.getInt("IDQ"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(int key) throws DataException{
        List<Answer> result = new ArrayList();
        
        try {
            this.getAnswersByQuestionId.setInt(1, key);
            
            try (ResultSet rs = getAnswersByQuestionId.executeQuery()) {
                while(rs.next()) {
                    result.add((Answer) getAnswerById(rs.getInt("ID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to create article object form ResultSet", ex);
        }
        return result;
    }

    @Override
    public Answer getAnswerById(int AnswerId) throws DataException {
        try {
            this.searchAnswerById.setInt(1, AnswerId);
            
            try ( ResultSet rs = this.searchAnswerById.executeQuery() ) {
                if (rs.next()) {
                    return createAnswer(rs);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }
        
        return null;
    }

    @Override
    public Answer getAnswerByQuestionIdParticipantId(int idp, int idq) throws DataException {
        try {
            this.getAnswerbyIdParticipantQuestionId.setInt(1, idp);
            this.getAnswerbyIdParticipantQuestionId.setInt(2, idq);


            try ( ResultSet rs = this.getAnswerbyIdParticipantQuestionId.executeQuery() ) {
                if (rs.next()) {
                    return createAnswer(rs);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Error from DataBase: ", ex);
        }

        return null;
    }

    @Override
    public void storeAnswer(Answer answer) throws DataException {
        
        try {
            insertAnswer.setInt(1, answer.getQuestion().getKey());
            insertAnswer.setInt(2,answer.getPartecipant().getKey());
            insertAnswer.setString(3, answer.getTextA().toString());
               insertAnswer.executeUpdate();
        } catch (SQLException ex) {
             throw new DataException("Error from DataBase: ", ex);
        }
         
    }



}
