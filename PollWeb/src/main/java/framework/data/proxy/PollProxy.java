/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;

import framework.data.DataLayer;
import java.util.List;
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
    protected int RespUserKey;
    
    public PollProxy(DataLayer d){
        super();
        this.dirty=false;
        this.dataLayer= d;
    }
    
    @Override
   public void setPartecipant(List<Partecipant> partecipant) {
       super.setPartecipant(partecipant);
       this.dirty= true;
   }
   
   
    @Override
    public void setRespUser(ResponsibleUser respUser) {
        super.setRespUser(respUser);
        this.dirty = true;
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
    
    
    public void setRespUserKey(int userKey) {
        this.RespUserKey = userKey;
    }
    
     @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        this.dirty= true;
    }
    
    @Override
    public void setQuestions(List<Question> questions) {
        super.setQuestions(questions);
        this.dirty= true;
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

    
    
}
