/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;

import framework.data.DataLayer;
import pollweb.data.impl.QuestionImpl;

/**
 *
 * @author giulia
 */
public class QuestionProxy  extends QuestionImpl {
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public QuestionProxy(DataLayer d){
        super();
        this.dirty= false;
        this.dataLayer= d;
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
