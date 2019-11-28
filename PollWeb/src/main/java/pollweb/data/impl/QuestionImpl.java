/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

/**
 *
 * @author achissimo
 */

import java.util.List;
import pollweb.data.model.Question;
import org.json.JSONObject;
import pollweb.data.model.Answer;
import pollweb.data.model.Poll;

public class QuestionImpl implements Question {

   
   
    private enum obbligate {
        yes, no
    }
    
    private enum type {
    shortText,
    longText, 
    numeric, 
    date, 
    singleChoice , 
    multipleChoice
    }
    
    private String textq, note;
    private String typeP;
    private boolean obbligated;
    private JSONObject possibleAnswer;
    private List<Answer> answer;
    private int key;

   
    
    public QuestionImpl() {
    }

    public QuestionImpl(int key,String textq, String note, String typeP, boolean obbligated, JSONObject possibleAnswer, List<Answer> answer) {
        this.textq = textq;
        this.note = note;
        this.typeP = typeP;
        this.obbligated = obbligated;
        this.possibleAnswer = possibleAnswer;
        this.answer = answer;
        this.key= key;
    }



    @Override
    public String getTextq() {
        return textq;
    }

    @Override
    public void setTextq(String textq) {
        this.textq = textq;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String getTypeP() {
        return typeP;
    }

    @Override
    public void setTypeP(String typeP) {
        this.typeP = typeP;
    }

    @Override
    public boolean getObbligated() {
        return obbligated;
    }
    
    @Override
    public void setObbligated(boolean obbligated) {
        this.obbligated = obbligated;
    }

    @Override
    public JSONObject getPossibleAnswer() {
        return possibleAnswer;
    }

    @Override
    public void setPossibleAnswer(JSONObject possibleAnswer) {
        this.possibleAnswer = new JSONObject(possibleAnswer);
    }
    
    @Override
     public List<Answer> getAnswer() {
        return answer;
    }

    @Override 
    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }
    
     @Override
    public int getKey() {
        return key;
    }

    @Override
    public void setKey(int key) {
        this.key= key;
    }

    
    
} 
