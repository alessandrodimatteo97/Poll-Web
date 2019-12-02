/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data.proxy;

import framework.data.DataLayer;
import pollweb.data.impl.ResponsibleUserImpl;

/**
 *
 * @author giulia
 */
public class ResponsibleUserProxy extends ResponsibleUserImpl{
    
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public ResponsibleUserProxy(DataLayer d){
        super();
        this.dirty= false;
        this.dataLayer= d;
    }
    
     @Override
    public void setNameR(String nameR) {
        super.setNameR(nameR);
        this.dirty= true;
    }
    
    @Override
    public void setSurnameR(String surnameR) {
        super.setSurnameR(surnameR);
        this.dirty= true;
    }
    
    @Override
    public void setFiscalCode(String fiscalCode) {
        super.setFiscalCode(fiscalCode);
        this.dirty= true;
    }
    
    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.dirty= true;
    }
    
     @Override
    public void setPwd(String pwd) {
        super.setPwd(pwd);
        this.dirty= true;
    }
    
      @Override
    public void setAdministrator(boolean administrator) {
        super.setAdministrator(administrator);
        this.dirty= true;
    }
    
    @Override 
    public void setAccepted(boolean accepted){
        super.setAccepted(accepted);
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
