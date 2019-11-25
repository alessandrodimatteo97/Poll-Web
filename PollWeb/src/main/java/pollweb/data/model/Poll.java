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
public interface Poll {

     List<Partecipant> getPartecipant();
    
     void setPartecipant(List<Partecipant> partecipant);
    
    
    ResponsibleUser getRespUser();

    void setRespUser(ResponsibleUser respUser);
    
    String getApertureText();

    String getCloserText();

    String getTitle();

    String getType();

    String getUrl();

    boolean isActivated();

    void setActivated(boolean activated);

    void setApertureText(String apertureText);

    void setCloserText(String closerText);

    void setTitle(String title);

    void setType(String type);

    void setUrl(String url);
    
}
