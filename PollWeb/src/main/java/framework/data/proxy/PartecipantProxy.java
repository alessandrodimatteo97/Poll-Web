/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;


import framework.data.DataLayer;
import pollweb.data.impl.PartecipantImpl;


/**
 *
 * @author giulia
 */
public class PartecipantProxy extends PartecipantImpl{
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public PartecipantProxy(DataLayer d){
        super();
        this.dirty= false;
        this.dataLayer= d;      
    }
    
  
    /*@Override
    public void setAnswer(List<Answer> answer){
        super.setAnswer(answer);
        this.dirty= true;
    }
    */
    @Override
    public void setApiKey(String ApiKey) {
        super.setApiKey(ApiKey);
        this.dirty= true;
    }
    
    @Override
    public void setNameP(String nameP){
        super.setNameP(nameP);
        this.dirty= true;
    }
    
    @Override
    public void setEmail(String email){
        super.setEmail(email);
        this.dirty= true;
    }
    
    @Override
    public void setPwd(String pwd){
        super.setPwd(pwd);
        this.dirty= true;
    }
    
    @Override
    public void setKey(int key){
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


