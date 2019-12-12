/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.data.dao;

import framework.data.DataException;
import framework.data.DataLayer;
import java.sql.SQLException;
import javax.sql.DataSource;
import pollweb.data.model.Answer;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;
import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author davide
 */
public class PollDataLayer extends DataLayer {

    public PollDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }
    
    @Override
    public void init() throws DataException {
        registerDAO(Poll.class, new PollDAO_MySQL(this));
        registerDAO(Partecipant.class, new PartecipantDAO_MySQL(this));
        registerDAO(Question.class, new QuestionDAO_MySQL(this));
        registerDAO(ResponsibleUser.class, new ResponsibleUserDAO_MySQL(this));
        registerDAO(Answer.class, new AnswerDAO_MySQL (this));
      //  registerDAO(Partecipant.class, new PartecipantDAO_MySQL(this));
      //  registerDAO(Question.class, new QuestionDAO_MySQL(this));
      //  registerDAO(ResponsibleUser.class, ResponsibleUser_MySQL(this));
    }
    
    public PollDAO getPollDAO() {
        return (PollDAO) getDAO(Poll.class);
    }
    
    public AnswerDAO getAnswerDAO() {
        return (AnswerDAO) getDAO(Answer.class);
    }
   
    public PartecipantDAO getPartecipantDAO() {
        return (PartecipantDAO) getDAO(Partecipant.class);
    }
   
    public QuestionDAO getQuestionDAO() {
       return (QuestionDAO) getDAO(Question.class);
    }
    
    public ResponsibleUserDAO getResponsibleUserDAO() {
        return (ResponsibleUserDAO) getDAO(ResponsibleUser.class);
    }
    
}
