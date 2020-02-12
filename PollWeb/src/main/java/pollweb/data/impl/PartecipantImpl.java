/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.Partecipant;

/**
 *
 * @author achissimo
 */
public class PartecipantImpl implements Partecipant {
    private String apiKey, nameP, email, pwd;
    private int key;
    
    public PartecipantImpl(){
        this.apiKey =  "";
        this.nameP =  "";
        this.email =  "";
        this.pwd =  "";
        this.key = 0;
    };

    public PartecipantImpl(int key,String apiKey, String nameP, String email, String pwd) {
        this.apiKey = apiKey;
        this.nameP = nameP;
        this.email = email;
        this.pwd = pwd;
        this.key= key;
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

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public void setKey(int key) {
        this.key=key;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Partecipant)){
            return false;
        }
        Partecipant other = (Partecipant) o;
        return (other.getKey() == this.key);
    }


    
    
    
  
}
