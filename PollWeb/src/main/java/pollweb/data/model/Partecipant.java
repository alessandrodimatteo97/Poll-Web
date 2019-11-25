/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import java.util.List;

/**
 *
 * @author achissimo
 */
public interface Partecipant {

    List<Answer> getAnswer();
    
   void setAnswer(List<Answer> answer);
    
    String getApiKey();

    String getEmail();

    String getNameP();

    String getPwd();

    void setApiKey(String apiKey);

    void setEmail(String email);

    void setNameP(String nameP);

    void setPwd(String pwd);
    
}
