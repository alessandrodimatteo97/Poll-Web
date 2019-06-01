/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.model;

import java.util.List;
import pollweb.data.impl.PossibleChoiseImpl;

/**
 *
 * @author achissimo
 */
public interface MultipleChoiseQuestion {

    List<PossibleChoiseImpl> getLpc();

    int getMaxM();

    int getMinM();

    void setLpc(List<PossibleChoiseImpl> lpc);

    void setMaxM(int maxM);

    // non mi ricordo bene come funzionava poi
    void setMinM(int minM);

    void setType(String type);

    String type();
    
}
