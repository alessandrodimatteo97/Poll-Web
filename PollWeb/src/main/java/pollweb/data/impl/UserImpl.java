/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;

import pollweb.data.model.User;

/**
 *
 * @author achissimo
 */
public class UserImpl implements User { // vedere se è meglio fare una sola classe
   private String name, surname, fiscalCode,email;
   private String type;
   private boolean compiled;
   public UserImpl(){}; // costruttore vuoto// costruttore vuoto
    
   // costruttore per il responsibleUser
   public UserImpl(String name, String surname, String fiscalCode, String email){
       this.name = name;
       this.surname = surname;
       this.fiscalCode = fiscalCode;
       this.email = email;
       this.type = "responsible";
   }
   
   // costruttore per il reservedUser
   public UserImpl(String name, String surname, String fiscalCode, String email, boolean compiled){
       this.name = name;
       this.surname = surname;
       this.fiscalCode = fiscalCode;
       this.email = email;
       this.compiled = compiled;
   }
   // costruttore per normal user
   public UserImpl(String name){
       this.name = name;
   }
   
    @Override
   public void setName(String name){
       this.name = name;
   }
    @Override
   public void setSurname(String surname){
       this.surname = surname;
   }
    @Override
   public void setFiscalCode(String fiscalCode){
       this.fiscalCode = fiscalCode;
   }
    @Override
   public void setEmail(String email){
       this.email = email;
   }
    @Override
   public void setCompiled(boolean c){
       this.compiled = c;
   }
    @Override
   public void setType(String type){
       this.type = type;
   }
   
    @Override
   public String getName(){
       return name;
   }
    @Override
   public String getSurname(){
       return surname;
   }
    @Override
   public String getFiscalcode(){
       return fiscalCode;
   }
    @Override
   public String getEmail(){
       return email;
   }
    @Override
   public boolean getCompiled(){
       return compiled;
   }
    @Override
   public String getType(){
       return type;
   }
   
}
