/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;

import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.dao.PollDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import pollweb.data.impl.QuestionImpl;
import pollweb.data.model.Poll;

/**
 *
 * @author giulia
 */
public class QuestionProxy  extends QuestionImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int poll_key = 0;
    
    public QuestionProxy(DataLayer d){
        super();
        this.dirty= false;
        this.dataLayer= d;
        this.poll_key= 0;
    }
    
     @Override
    public void setTextq(String textq){
        super.setTextq(textq);
        this.dirty= true;
    }
    
    @Override
    public void setNote(String note) {
        super.setNote(note);
        this.dirty= true;
    }
    
    @Override
    public void setTypeP(String typeP) {
        super.setTypeP(typeP);
        this.dirty= true;
    }
    
     @Override
    public void setObbligated(boolean obbligated) {
        super.setObbligated(obbligated);
        this.dirty= true;
    }
    
    @Override
    public void setPossibleAnswer(JSONObject possibleAnswer) {
        super.setPossibleAnswer(possibleAnswer);
        this.dirty = true;
    }
    
    @Override 
    public void setAnswer(List<String> answer) {
        super.setAnswer(answer);
        this.dirty = true;
    }
    
   
     /*public List<Answer> getAnswer(){
         if (super.getAnswer() == null){
             try {
                 super.setAnswer(((AnswerDAO) dataLayer.getDAO(Answer.class)).getAnswersByQuestionId(this.poll_key));
             } catch (DataException ex) {
                 Logger.getLogger(QuestionProxy.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         return super.getAnswer();
     }*/
    @Override
    public void setKey(int key) {
        super.setKey(key);
        this.dirty= true;
    }
       //METODI DEL PROXY
    //PROXY-ONLY METHODS
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
    
    public void setPollKey(int poll_key){
        this.poll_key = poll_key;
    }
    
    public int getPollKey(){
        return this.poll_key;
    }
    
    @Override
    public void setPoll(Poll poll){
        super.setPoll(poll);
        if(poll!=null) this.poll_key = poll.getKey();
        else { this.poll_key = 0; }
        this.dirty = true;
    }
    @Override
    public Poll getPoll(){
        
   if (super.getPoll()== null && poll_key > 0) {
            try { // .getIssue(issue_key));
                super.setPoll(((PollDAO) dataLayer.getDAO(Poll.class)).getPollById(poll_key));
            } catch (DataException ex) {
                Logger.getLogger(PollProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getPoll();
    }
}
