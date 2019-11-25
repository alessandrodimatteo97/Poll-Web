/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import java.util.List;
import pollweb.data.model.Answer;
import pollweb.data.model.Partecipant;

/**
 *
 * @author achissimo
 */
public class PartecipantImpl implements Partecipant {
    private String apiKey, nameP, email, pwd;
    private List<Answer> answer;
    
    public PartecipantImpl(){};

    public PartecipantImpl(String apiKey, String nameP, String email, String pwd, List<Answer> answer) {
        this.apiKey = apiKey;
        this.nameP = nameP;
        this.email = email;
        this.pwd = pwd;
        this.answer = answer;
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
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getNameP() {
        return nameP;
    }

    @Override
    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    
    
    
    
    
  
}
