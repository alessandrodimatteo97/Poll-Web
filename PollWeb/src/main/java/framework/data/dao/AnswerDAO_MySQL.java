/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;
import pollweb.data.model.Answer;

/**
 *
 * @author davide
 */
public class AnswerDAO_MySQL extends DAO implements AnswerDAO {

    public AnswerDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public Answer createAnswer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Answer createAnswer(ResultSet rs) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Answer> getAnswersByQuestionId(int QuestionId) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Answer getAnswerById(int AnswerId) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
