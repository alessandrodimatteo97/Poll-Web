/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;

import framework.data.DataLayer;
import org.json.JSONObject;
import pollweb.data.impl.AnswerImpl;


/**
 *
 * @author giulia
 */
public class AnswerProxy extends AnswerImpl{
    
    protected boolean dirty;
    protected int partecipantKey, questionKey;
    protected DataLayer dataLayer;
    
    public AnswerProxy(DataLayer d) {
        super();
        this.dirty= false;
        this.dataLayer= d;
        this.partecipantKey = 0;
        this.questionKey = 0;
    }
    
    @Override
    public void setTextA(JSONObject textA){
        super.setTextA(textA);
        this.dirty= true;
    }

    @Override 
    public void setKey(int key){
        super.setKey(key);
        this.dirty= true;
    }

    public void setPartecipantKey(int key){
        this.partecipantKey = key;
        this.dirty= true;
    }

    public void setQuestionKey(int key){
        this.questionKey = key;
        this.dirty= true;
    }
  /*METODI DEL PROXY*/
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
       
   
}
