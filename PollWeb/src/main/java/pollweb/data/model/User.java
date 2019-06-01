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
public interface User {

    boolean getCompiled();

    String getEmail();

    String getFiscalcode();

    String getName();

    String getSurname();

    String getType();

    void setCompiled(boolean c);

    void setEmail(String email);

    void setFiscalCode(String fiscalCode);

    void setName(String name);

    void setSurname(String surname);

    void setType(String type);
    
}
