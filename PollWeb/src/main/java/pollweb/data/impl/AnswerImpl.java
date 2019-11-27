/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.Answer;
import org.json.JSONObject;
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
    
    public AnswerImpl(){}
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
        this.textA = new JSONObject(textA);
    }
    
    @Override 
    public void setKey(int key){
        this.key= key;
    }
   
}
