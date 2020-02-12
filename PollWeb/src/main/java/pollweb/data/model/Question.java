/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import java.util.List;
import javax.json.JsonObject;
import org.json.JSONObject;
import pollweb.data.impl.QuestionImpl;
/**
 *
 * @author achissimo
 */
public interface Question {

    String getNote();


    JSONObject getPossibleAnswer();

    String getTextq();
    
    String getTypeP();
    
    boolean getObbligated();
    
    int getKey();

    void setObbligated(boolean obbligated);
            
    void setNote(String note);

    void setPossibleAnswer(JSONObject possibleAnswer);

    void setTypeP(String typeP);
            
    void setTextq(String textq);

     List<String> getAnswer();

     void setAnswer(List<String> answer);
     
     void setKey(int key);
     
     void setPoll(Poll poll);
     
     Poll getPoll();

     void setNumber(int number);

     int getNumber();
    
    
}

// 1 issue ha più articoli, praticamente c'è un campo issue che rappresenta la chiave esterna di articol, stessa cosa per quanto riguarda Question, dove nella classe c'è bisogno 
// del campo Poll, e nel proxy bisogna settare la chiave;