/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;
import pollweb.data.model.OpenQuestion;
import java.util.Date;
/**
 *
 * @author achissimo
 */
public class OpenQuestionImpl extends QuestionImpl implements OpenQuestion{
    Date minDate, maxDate;
    int valMin, valMax;
    public OpenQuestionImpl(){
        super();
    }
    public OpenQuestionImpl(String text, String note, Boolean obbligated, int position, Date minDate, Date maxDate, int valMin, int valMax) {
        super(text, note, obbligated, position);
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.valMin = valMin;
        this.valMax = valMax;
    }
    @Override
    public void setMinDate(Date minDate){
        this.minDate = minDate;
    }
    @Override
    public void setMaxDate(Date maxDate){
        this.maxDate = maxDate;
    }
    @Override
    public void setValMin(int valMin){
        this.valMin = valMin;
    }
    @Override
    public void setValMax(int valMax){
        this.valMax = valMax;
    }
    @Override
    public Date getMinDate(){
        return minDate;
    }
    @Override
    public Date getMindDate(){
        return maxDate;
    }
    @Override
    public int getValMin(){
        return valMin;
    }
    @Override
    public int getValMax(){
        return valMax;
    }
    
}
