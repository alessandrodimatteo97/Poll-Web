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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import pollweb.data.model.Answer;
import pollweb.data.model.Question;

/**
 *
 * @author davide
 */
public class QuestionDAO_MySQL extends DAO implements QuestionDAO {
    
    private PreparedStatement createQuestion;
    private PreparedStatement getQuestionsByPollId, getQuestionById;
    private PreparedStatement updateQuestion, deleteQuestion;
    private PreparedStatement getNumberQuestion;
    private PreparedStatement checkQuestionPoll, checkQuestionUser;
    public QuestionDAO_MySQL(DataLayer d) {
        super(d);
    }

     @Override
    public void init() throws DataException {
        try {
            super.init();
            getNumberQuestion = connection.prepareStatement("SELECT COUNT(ID) AS number FROM question where IDP = ?");
            getQuestionById = connection.prepareStatement("SELECT * FROM question  WHERE ID=? order by number");
            getQuestionsByPollId = connection.prepareStatement("SELECT * FROM question JOIN poll ON question.IDP=poll.ID WHERE poll.ID=? order by number");
            updateQuestion = connection.prepareStatement("UPDATE question SET textq=?,typeq=?,note=?,obbligation=?,possible_answer=?, number=? WHERE ID=?");
            deleteQuestion = connection.prepareStatement("DELETE FROM question WHERE ID=?");
            createQuestion = connection.prepareStatement("INSERT INTO question (textq, typeq,note,obbligation,IDP, possible_answer) VALUES(?,?,?,?,?, ?)", Statement.RETURN_GENERATED_KEYS);
            checkQuestionPoll = connection.prepareStatement("SELECT  * FROM question WHERE ID=? AND IDP=?");
            checkQuestionUser = connection.prepareStatement("SELECT question.* FROM question, poll, responsibleUser WHERE question.IDP = poll.ID AND poll.idR = responsibleUser.ID and question.ID=? and responsibleUser.token=?");
             
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

            if(rs.getString("possible_answer")!=null){
               JSONObject j = new JSONObject(rs.getString("possible_answer"));
                question.setPossibleAnswer(j);
            }
            if(rs.getString("obbligation").equals("yes")){
                question.setObbligated(true);
            } else {
                question.setObbligated(false);
            }
            question.setNumber(rs.getInt("number"));
            question.setTypeP(rs.getString("typeq"));
            question.setTextq(rs.getString("textq"));
            question.setPollKey(rs.getInt("IDP"));
        } catch (SQLException ex) {
            System.out.println("Errore nel chiudere gli statement" + ex); 
        }
        
        return question;
    }

    @Override
    public boolean deleteQuestion(int question_key) throws DataException {
        try {
            this.deleteQuestion.setInt(1, question_key);


            return  (this.deleteQuestion.executeUpdate() == 1);

        } catch (SQLException ex) {
                throw new DataException("Unable to delete question by ID", ex);

            }
    }

    @Override
    public boolean setQuestionType(Question question, String type) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject setPossibleAnswers(Question question, JSONObject possibleAnswers) throws DataException{
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
    public Question getQuestionById(ResultSet rs) throws DataException{
          QuestionProxy question = createQuestion();
        try {
           question.setKey(rs.getInt("ID"));
           question.setNote(rs.getString("note"));
           question.setTextq(rs.getString("textq"));
           question.setTypeP(rs.getString("typeq"));
           if(rs.getString("obbligation").equals("yes")){
                question.setObbligated(true);
            } else {
                question.setObbligated(false);
            }
           question.setPollKey(rs.getInt("IDP"));
          if (rs.getString("possible_answer") != null){
              JSONObject j = new JSONObject(rs.getString("possible_answer"));
              question.setPossibleAnswer(j);
              
          }
              
          
        }
        catch (SQLException ex) {
            throw new DataException("Unable to load question by ID", ex);

         }

        return question;   
}


    @Override
    public Question getQuestionById(int keyQ) throws DataException {
        Question result ;
        try {
            getQuestionById.setInt(1, keyQ);
            ResultSet rs = getQuestionById.executeQuery();
            if(rs.next()) {
                result = (Question) getQuestionById(rs);
            }  else {
                result = null;
            }

        } catch (SQLException ex) {
            throw new DataException("Unable to load question by id", ex);

        }
        return result;
    }

    @Override
    public List<Question> getQuestionsByPollId(int keyPoll) throws DataException{
             List<Question> result = new ArrayList();
             
        try {
            getQuestionsByPollId.setInt(1, keyPoll);
            try (ResultSet rs = getQuestionsByPollId.executeQuery()) {
                while (rs.next()) {
                    result.add((Question) getQuestionById(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load question by id poll", ex);

        }
        return result;
    }

    @Override
    public List<Question> getQuestionsByUserId(int userId) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override // typeq,textq,note,obbligation,possible_answer,IDP
    public void store(Question question) throws DataException {
        int key = question.getKey();
        try {
            if (question.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (question instanceof QuestionProxy && !((QuestionProxy) question).isDirty()) {
                    return;
                } //"UPDATE question SET textq=?,typeq=?,typeq=?,obbligation=?,possible_answer=? WHERE IDP=?")
                    updateQuestion.setString(1, question.getTextq());
                    updateQuestion.setString(2, question.getTypeP());
                    updateQuestion.setString(3, question.getNote());
                    updateQuestion.setString(4, "no");
                if(question.getPossibleAnswer()!=null) updateQuestion.setString(5, question.getPossibleAnswer().toString());
                else updateQuestion.setNull(5, Types.VARCHAR);

                updateQuestion.setInt(6, question.getNumber());

                    updateQuestion.setInt(7, key);


                updateQuestion.executeUpdate();
            } else { //insert
                    createQuestion.setString(1, question.getTextq());
                    createQuestion.setString(2, question.getTypeP());
                    createQuestion.setString(3, question.getNote());
                    if (question.getObbligated()) createQuestion.setString(4, "yes");
                    else createQuestion.setString(4, "no");
                if(question.getPossibleAnswer()!=null) createQuestion.setString(6, question.getPossibleAnswer().toString());
                else createQuestion.setNull(6, Types.VARCHAR);

                if (question.getPoll()!= null) {
                    createQuestion.setInt(5, question.getPoll().getKey());
                } else {
                    createQuestion.setNull(5, java.sql.Types.INTEGER);
                }

                if (createQuestion.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = createQuestion.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                    //aggiornaimo la chiave in caso di inserimento
                    //after an insert, uopdate the object key
                    question.setKey(key);
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                article.copyFrom(getArticle(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (question instanceof QuestionProxy) {
                ((QuestionProxy) question).setDirty(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }

    @Override
    public int getQuestionNumber(int poll_key) throws DataException {
        try {
            getNumberQuestion.setInt(1, poll_key);
            ResultSet rs = getNumberQuestion.executeQuery();
            if(rs.next()){
                return rs.getInt("number");
            }
            else {
                return 0;
            }
        }
catch (SQLException ex) {
            throw new DataException("Unable to count question of poll", ex);
        }

    }

    @Override
   public boolean checkQuestionPoll(int poll_key, int question_key) throws DataException{
        try {
            if(question_key == 0) return true;
            checkQuestionPoll.setInt(1,question_key);
            checkQuestionPoll.setInt(2, poll_key);
            return checkQuestionPoll.executeQuery().next();
        }
        catch(SQLException ex) {
            throw new DataException("question is not included in the poll, incorrect id_question or id_poll, ", ex);

        }
    }

    @Override
    public boolean checkQuestionUser(int question_key, String token) throws DataException {
        try {
            checkQuestionUser.setInt(1, question_key);
            checkQuestionUser.setString(2, token);
            return checkQuestionUser.executeQuery().first();
        }
        catch(SQLException ex) {
            throw new DataException(" ", ex);

        }   
    }



}
