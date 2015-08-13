package model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Tangent Chang on 6/14/15.
 */
public class Automobile implements Serializable{
    private String maker;
    private String modelName;
    private float basePrice;
    private List<OptionSet> optionsets;
    private Map<String, OptionSet.Option> choices;

    public Automobile(){
        optionsets = new ArrayList<OptionSet>();
        choices = new HashMap<String, OptionSet.Option>();
    }

    public String getMaker(){ return maker;}
    public void setMaker(String make) {maker = make;}
    public String getModelName(){return modelName;}
    public void setModelName(String n){ modelName = n;}
    public float getBasePrice(){return basePrice;}
    public void setBasePrice(float p){ basePrice = p;}

    public List<OptionSet> getOptionsets(){ return optionsets;}
    public void setOptionsets(List<OptionSet> optionsets){
        this.optionsets = optionsets;
    }
    public OptionSet getOptionset(int setIndex){return optionsets.get(setIndex);}
    public String getOptionsetName(int setIndex){
        return optionsets.get(setIndex).getName();
    }
    public void setOptionset(String setName){
        optionsets.add(new OptionSet(setName));
    }
    public List<OptionSet.Option> getOptions(int setIndex){
        return getOptionset(setIndex).getOptions();
    }
    public synchronized void setOption(int setIndex, String optName, float optPrice){
        //optSets[i] = new OptionSet(setName);
        optionsets.get(setIndex).setOption(optName,optPrice);
    }
    public synchronized void setOption(String setName, String optName, float optPrice){
        optionsets.get(findOptionsetByName(setName)).setOption(optName, optPrice);
    }
    public String getOptionName(String setName, int optionIndex){
        int setIndex = findOptionsetByName(setName);
        String optionName = getOptionset(setIndex).getOption(optionIndex).getName();
        return optionName;
    }
    public float getOptionPrice(String setName, int optionIndex){
        int setIndex = findOptionsetByName(setName);
        float price = getOptionset(setIndex).getOption(optionIndex).getPrice();
        return price;
    }
    public String getChoice(String setName){
        return choices.get(setName).getName();
    }
    public void setChoice(String setName, String optName){
        int i = findOptionsetByName(setName);
        int j = findOptionByName(i,optName);
        choices.put(setName, optionsets.get(i).getOption(j));
    }
    public float getChoicePrice(String setName){
        //int i = findOptionsetByName(setName);
        return choices.get(setName).getPrice();
    }
    public float getTotalPrice(){
        float sum = basePrice;
        for(int i = 0; i< optionsets.size(); i++){
            String key = optionsets.get(i).getName();
            float tempPrice = choices.get(key).getPrice();
            sum = sum + tempPrice;
        }
        return sum;
    }

    //get the index of optionset
    public int findOptionsetByName(String optsetName){
        for(int i=0;i<optionsets.size();i++){
            if(optionsets.get(i) == null){ }
            else if(optionsets.get(i).getName().equals(optsetName)){return i;}
        }
        return -1;
    }
    //get the index of option
    public int findOptionByName(int setIndex, String optName){
        int i = optionsets.get(setIndex).findOptionByName(optName);
        return i;
    }

    public void deleteOptionset(int setIndex){
        optionsets.remove(setIndex);
    }
    public synchronized void deleteOption(int setIndex, int optIndex){
        optionsets.get(setIndex).deleteOption(optIndex);
    }

    //only update price
    /*public synchronized void updateOption(int setIndex, int optIndex, float p){
        optionsets.get(setIndex).updateOption(optIndex, p);
    }*/
    public synchronized void updateOption(String optsetName, String optName, String n, float p){
        System.out.println(Thread.currentThread().getName() + " is running");
        optionsets.get(findOptionsetByName(optsetName)).updateOption(optName, n, p);
        notifyAll();

    }
    //only update price
    /*public synchronized void updateOption(String optsetName, String optName, float newPrice){
        int i = findOptionsetByName(optsetName);
        int j = findOptionByName(i, optName);
        optionsets.get(i).updateOption(j, newPrice);
    }*/

    public void updateOptionset(String setName, String newName){
        int i = findOptionsetByName(setName);
        optionsets.get(i).setName(newName);
    }

    public void print(){
        System.out.format("Name: %s %nBase Price: %f %nSets quantity: %d %n", modelName, basePrice, optionsets.size());
        for(int i=0; i<optionsets.size(); i++){
            System.out.print(optionsets.get(i).getName()+"\n");
            for(int j=0; j<optionsets.get(i).getOptions().size(); j++){
                System.out.print(" "+optionsets.get(i).getOption(j).getName()+", "+optionsets.get(i).getOption(j).getPrice()+"\n");
            }
        }

    }
}
