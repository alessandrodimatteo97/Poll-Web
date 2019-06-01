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
public interface Question {

    String getNote(String note);

    boolean getObbligated();

    int getPosition();

    String getText(String text);

    void setNote(String note);

    void setObbligate(Boolean obbligated);

    void setPosition(int position);

    void setText(String text);
    
}
