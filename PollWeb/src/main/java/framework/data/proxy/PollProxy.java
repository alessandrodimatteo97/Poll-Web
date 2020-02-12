/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;


import framework.data.DataException;
import framework.data.DataLayer;
import framework.data.dao.PartecipantDAO;
import framework.data.dao.QuestionDAO;
import framework.data.dao.ResponsibleUserDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollweb.data.impl.PollImpl;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Question;
import pollweb.data.model.ResponsibleUser;


/**
 *
 * @author giulia
 */
public class PollProxy extends PollImpl{
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int RespUserKey = 0;
    
    
    public PollProxy(DataLayer d){
        super();
        this.dirty=false;
        this.dataLayer= d;
        this.RespUserKey= 0;
    }
    
    @Override
   public void setPartecipant(List<Partecipant> partecipant) {
       super.setPartecipant(partecipant);
       this.dirty= true;
   }
   
   @Override
   public List<Partecipant> getPartecipant(){
       if (super.getPartecipant() == null){
           try {
               super.setPartecipant(((PartecipantDAO) dataLayer.getDAO(Partecipant.class)).getPartecipants());
           }catch (DataException ex) {
              Logger.getLogger(PollProxy.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return super.getPartecipant();
   }
   
   
    @Override
    public void setRespUser(ResponsibleUser respUser) {
        super.setRespUser(respUser);
        this.dirty = true;
    }
    
    @Override
    public ResponsibleUser getRespUser(){
        if (super.getRespUser() == null && RespUserKey > 0) {
            try {
                super.setRespUser(((ResponsibleUserDAO) dataLayer.getDAO(ResponsibleUser.class)).getResponsibleUser(RespUserKey));
            } catch (DataException ex) {
                Logger.getLogger(PollProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getRespUser();
    }
    
    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        this.dirty= true;
    }
    
     @Override
    public void setApertureText(String apertureText) {
        super.setApertureText(apertureText);
        this.dirty = true;
    }
    
     @Override
    public void setCloserText(String closerText) {
        super.setCloserText(closerText);
        this.dirty= true;
    }
    
     @Override
    public void setType(String type) {
        super.setType(type);
        this.dirty = true;
    }
    
    @Override
    public void setUrl(String url) {
        super.setUrl(url);
        this.dirty= true;
    }
    
    
 
    public void setActivated(String activated) {
        if(activated.equals("yes")) {
            super.setActivated(true);
        } else {
            super.setActivated(false);
        }
        this.dirty= true;
    }

    public void setAlreadyActivated(String activated) {
        if(activated.equals("yes")) {
            super.setAlreadyActivated(true);
        } else {
            super.setAlreadyActivated(false);
        }
        this.dirty= true;
    }
    
    @Override
    public void setQuestions(List<Question> questions) {
        super.setQuestions(questions);
        this.dirty= true;
    }
    
     @Override
    public List<Question> getQuestions() {
        if (super.getQuestions() == null){
            try {
                super.setQuestions(((QuestionDAO) dataLayer.getDAO(Question.class)).getQuestionsByPollId(super.getKey()));
            } catch (DataException ex) {
                Logger.getLogger(PollProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return super.getQuestions();
    }
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

       public void setRespUserKey(int userKey) {
        this.RespUserKey = userKey;
    }

    
}
