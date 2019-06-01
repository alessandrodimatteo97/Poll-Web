/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.Question;

/**
 *
 * @author achissimo
 */
public class QuestionImpl implements Question {
    String text, note;
    boolean obbligated;
    int position;
    
    public QuestionImpl(){};
    public QuestionImpl(String text, String note, Boolean obbligated, int position){
        this.text = text;
        this.note = note;
        this.obbligated = obbligated;
        this.position = position;
    }
    
    @Override
    public void setText(String text){
        this.text = text;
    }
    
    @Override
    public void setNote(String note){
        this.note = note;
    }
    @Override
    public void setObbligate(Boolean obbligated){
        this.obbligated = obbligated;
    }
    
    @Override
    public void setPosition(int position ){
        this.position = position;
    }
    
    @Override
    public String getText(String text){
        return text;
    }
    
    @Override
    public String getNote(String note){
        return note;
    }
    @Override
    public boolean getObbligated(){
        return obbligated;
}
    @Override
    public int getPosition(){
        return position;
    }
}
