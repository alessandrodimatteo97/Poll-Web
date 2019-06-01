/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.PossibleChoise;

/**
 *
 * @author achissimo
 */
public class PossibleChoiseImpl implements PossibleChoise {
    String textPC;
    
    public PossibleChoiseImpl(){}
    
    public PossibleChoiseImpl(String textPC){
       this.textPC=textPC;
    }
    @Override
    public void setTextPC(String tpc){
        this.textPC = tpc;
    }
    @Override
    public String getTextPC(){
        return textPC;
    }
}
