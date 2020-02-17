/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;


/**
 *
 * @author achissimo
 */
public interface Partecipant {

    
    String getApiKey();

    String getEmail();

    String getNameP();

    String getPwd();
    
    
    int getKey();

    void setApiKey(String apiKey);

    void setEmail(String email);

    void setNameP(String nameP);

    void setPwd(String pwd);
    
    void setKey(int key);
  
    
}
