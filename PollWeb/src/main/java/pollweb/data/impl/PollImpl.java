/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import java.util.List;
import pollweb.data.model.Partecipant;
import pollweb.data.model.Poll;
import pollweb.data.model.Question;
import pollweb.data.model.ResponsibleUser;
/**
 *
 * @author achissimo
 */

public class PollImpl implements Poll {

    
    private  String title, apertureText, closerText, type, url;
    private boolean activated;
    private List<Question> questions;
    private List<Partecipant> partecipant; // inteso come reservedPartecipant
    private ResponsibleUser respUser;
    private int key;

    public PollImpl(int key,String title, String apertureText, String closerText, String type, String url, boolean activated, List<Question> questions, List<Partecipant> partecipant, ResponsibleUser respUser) {
        this.title = title;
        this.apertureText = apertureText;
        this.closerText = closerText;
        this.type = type;
        this.url = url;
        this.activated = activated;
        this.questions = questions;
        this.partecipant = partecipant;
        this.respUser = respUser;
        this.key = key;
    }

    public PollImpl() {}
    
    @Override
    public List<Partecipant> getPartecipant() {
        return partecipant;
    }
    
    @Override
    public void setPartecipant(List<Partecipant> partecipant) {
        this.partecipant = partecipant;
    }
    
    @Override
    public ResponsibleUser getRespUser() {
        return respUser;
    }

    @Override
    public void setRespUser(ResponsibleUser respUser) {
        this.respUser = respUser;
    }
    
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getApertureText() {
        return apertureText;
    }

    @Override
    public void setApertureText(String apertureText) {
        this.apertureText = apertureText;
    }

    @Override
    public String getCloserText() {
        return closerText;
    }

    @Override
    public void setCloserText(String closerText) {
        this.closerText = closerText;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
    public String toString(){
        return title +' '+ apertureText;
    }
    
    
  
}


