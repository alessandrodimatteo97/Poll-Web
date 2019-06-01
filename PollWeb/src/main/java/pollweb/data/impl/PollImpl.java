/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;
import pollweb.data.model.Poll;
import java.util.*;
/**
 *
 * @author achissimo
 */
public class PollImpl implements Poll {
    String title, apertureText, closerText, typeP, url;
    Boolean activated;
    List<QuestionImpl> question;
    public PollImpl(){}
    
    public PollImpl(String title, String apertureText, String closerText, String type, String url, Boolean activated, List<QuestionImpl> q){
        this.title = title;
        this.apertureText = apertureText;
        this.closerText = closerText;
        this.typeP = type;
        this.activated = activated;
        this.question = q;
        // aggiungere la lista di domande
    }
    
    @Override
    public void setTitle(String title){
        this.title = title;
    }
    @Override
    public void setApertureText(String apertureText){
        this.apertureText = apertureText;
    }
    @Override
    public void setCloserText(String closerText){
        this.closerText=closerText;
    }
    @Override
    public void setTypeP(String typeP){
        this.typeP = typeP;
    }
    @Override
    public void setUrl(String url){
        this.url = url;
    }
    @Override
    public void setActivated(Boolean a){
        this.activated = a;
    }
    @Override
    public void setQuestion(List<QuestionImpl> q){
        this.question = q;
    }
    @Override
    public String getTitle(){
        return title;
    }
    @Override
    public String getApertureText(){
        return apertureText;
    }
    @Override
    public String getCloserText(){
        return closerText;
    }
    @Override
    public String getTypeP(){
        return typeP;
    }
    @Override
    public Boolean getActivated(){
        return activated;
    }
    
    @Override
    public List<QuestionImpl> getQuestion (){
    return question;
}
    
}
