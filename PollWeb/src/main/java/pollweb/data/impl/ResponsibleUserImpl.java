/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.ResponsibleUser;

/**
 *
 * @author achissimo
 */

/*
nameR varchar(100) not null,
surnameR varchar(100),
fiscalCode varchar(16) unique,
email varchar(100) not null,
pwd varchar(16) not null,
administrator enum('yes', 'no') not null default 'no'
*/
public class ResponsibleUserImpl implements ResponsibleUser {
    private String nameR, surnameR, fiscalCode, email, pwd;
    private enum admin{ yes, no };
    private admin administrator;
    
    public ResponsibleUserImpl() {}

    public ResponsibleUserImpl(String nameR, String surnameR, String fiscalCode, String email, String pwd, admin administrator) {
        this.nameR = nameR;
        this.surnameR = surnameR;
        this.fiscalCode = fiscalCode;
        this.email = email;
        this.pwd = pwd;
        this.administrator = administrator;
    }

    @Override
    public String getNameR() {
        return nameR;
    }

    @Override
    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    @Override
    public String getSurnameR() {
        return surnameR;
    }

    @Override
    public void setSurnameR(String surnameR) {
        this.surnameR = surnameR;
    }

    @Override
    public String getFiscalCode() {
        return fiscalCode;
    }

    @Override
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
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

    public admin getAdministrator() {
        return administrator;
    }

    public void setAdministrator(admin administrator) {
        this.administrator = administrator;
    }
    
    
    
    
}
