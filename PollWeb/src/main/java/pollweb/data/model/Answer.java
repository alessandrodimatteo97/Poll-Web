/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import org.json.JSONObject;
/**
 *
 * @author achissimo
 */
public interface Answer {

    JSONObject getTextA();
    
    int getKey();
    
   Question getQuestion();
    
    Partecipant getPartecipant();

    void setTextA(JSONObject textA);
    
    void setKey(int key);
    
    void setQuestion(Question question);
    
    void setPartecipant(Partecipant partecipant);
}
