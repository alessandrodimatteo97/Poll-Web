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

/**
 *
 * @author giulia
 */
public class PollProxy extends PollImpl{
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
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
    
      //METODI DEL PROXY
    //PROXY-ONLY METHODS
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
    
}
