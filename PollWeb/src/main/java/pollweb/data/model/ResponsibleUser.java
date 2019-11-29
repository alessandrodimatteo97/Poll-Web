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
public interface ResponsibleUser {

    String getEmail();

    String getFiscalCode();

    String getNameR();

    String getPwd();

    String getSurnameR();
    
    boolean getAdministrator();
    
    int getKey();

    void setAdministrator(boolean administrator);
    
    void setEmail(String email);

    void setFiscalCode(String fiscalCode);

    void setNameR(String nameR);

    void setPwd(String pwd);

    void setSurnameR(String surnameR);
    
    void setKey(int key);
    
}
