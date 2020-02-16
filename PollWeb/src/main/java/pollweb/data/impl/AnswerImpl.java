/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.Answer;
import org.json.JSONObject;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Question;
/**
 *
 * @author achissimo
 */
/*
ID integer unsigned not null primary key auto_increment,
IDQ integer unsigned not null,
ID_P integer unsigned not null,
texta json not null,
*/
public class AnswerImpl implements Answer {
    private JSONObject textA;
    private int key;
    private Question question;
    private Partecipant partecipant;
    
    public AnswerImpl(){
    this.textA = null;
    this.key=0;
    this.question=null;
    this.partecipant = null;
    }
    
    public AnswerImpl(int key,JSONObject textA){
        this.textA = new JSONObject(textA);
        this.key=key;
    }

    @Override
    public JSONObject getTextA() {
        return textA;
    }
    
    @Override 
    public int getKey(){
        return key;
    }

    @Override
    public void setTextA(JSONObject textA) {
        this.textA = textA;
    }
    
    @Override 
    public void setKey(int key){
        this.key= key;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public Partecipant getPartecipant() {
        return partecipant;
    }

    @Override
    public void setQuestion(Question question) {
        this.question=question;
    }

    @Override
    public void setPartecipant(Partecipant partecipant) {
        this.partecipant=partecipant;
    }
   
}
