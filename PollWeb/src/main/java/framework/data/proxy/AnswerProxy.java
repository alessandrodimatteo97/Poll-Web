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
    protected DataLayer dataLayer;
    
    public AnswerProxy(DataLayer d) {
        super();
        this.dirty= false;
        this.dataLayer= d;
    }
    
    @Override
  public void setTextA(JSONObject textA){
      super.setTextA(textA);
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
