/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import java.util.List;
import pollweb.data.impl.QuestionImpl;

/**
 *
 * @author achissimo
 */
public interface Poll {

    Boolean getActivated();

    String getApertureText();

    String getCloserText();

    List<QuestionImpl> getQuestion();

    String getTitle();

    String getTypeP();

    void setActivated(Boolean a);

    void setApertureText(String apertureText);

    void setCloserText(String closerText);

    void setQuestion(List<QuestionImpl> q);

    void setTitle(String title);

    void setTypeP(String typeP);

    void setUrl(String url);
    
}
