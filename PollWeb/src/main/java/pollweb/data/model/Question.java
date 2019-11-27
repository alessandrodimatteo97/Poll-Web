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

     List<Answer> getAnswer();

     void setAnswer(List<Answer> answer);
     
     void setKey(int key);

    
    
}
