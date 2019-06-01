/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollweb.data.impl;
import pollweb.data.model.MultipleChoiseQuestion;
import java.util.List;
/**
 *
 * @author achissimo
 */
public class MultipleChoiseQuestionImpl extends QuestionImpl implements MultipleChoiseQuestion{
    private int minM, maxM;
    private String type;
    private List<PossibleChoiseImpl> lpc;
    // esso ha una lista di possibili risposte, da aggiugere
    public MultipleChoiseQuestionImpl(){
        super();
    }
    public MultipleChoiseQuestionImpl(String text, String note, Boolean obbligated, int position, int minM, int maxM, List<PossibleChoiseImpl> lpc) {
        super(text, note, obbligated, position);
        this.minM=minM;
        this.maxM=maxM;
        this.lpc = lpc;
    }
    // costruttore vuoto con la sola chiamata alla superclasse;
    public MultipleChoiseQuestionImpl(String text, String note, Boolean obbligated, int position){super(text, note, obbligated, position);};
    // non mi ricordo bene come funzionava poi
    @Override
    public void setMinM(int minM){
        this.minM = minM;
    }
    @Override
    public void setMaxM(int maxM){
        this.maxM = maxM;
    }
    @Override
    public void setType(String type){
        this.type = type;
    }
    @Override
    public void setLpc(List<PossibleChoiseImpl> lpc){
        this.lpc = lpc;
    }
    @Override
    public int getMinM(){
        return minM;
    }
    @Override
    public int getMaxM(){
        return maxM;
    }
    @Override
    public String type(){
        return type;
    }
    @Override
    public List<PossibleChoiseImpl> getLpc(){
        return lpc;
    }
}
