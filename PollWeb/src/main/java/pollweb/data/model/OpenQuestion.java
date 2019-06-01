/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import java.util.Date;

/**
 *
 * @author achissimo
 */
public interface OpenQuestion {

    Date getMinDate();

    Date getMindDate();

    int getValMax();

    int getValMin();

    void setMaxDate(Date maxDate);

    void setMinDate(Date minDate);

    void setValMax(int valMax);

    void setValMin(int valMin);
    
}
