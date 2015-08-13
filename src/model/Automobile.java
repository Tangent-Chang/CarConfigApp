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
    //public String getModel(){return modelName;}
    //public void setModel(String n){ modelName = n;}
    public float getBasePrice(){return basePrice;}
    public void setBasePrice(float p){ basePrice = p;}

    public OptionSet getOptionset(int setIndex){return optionsets.get(setIndex);}
    public String getOptionsetName(int setIndex){
        return optionsets.get(setIndex).getName();
    }

    public void setOptionset(String setName){
        optionsets.add(new OptionSet(setName));
    }

    public List<OptionSet> getOptionsets(){ return optionsets;}
    public ArrayList<String> getOptionsetsNames(){
        ArrayList<String> setsNames = new ArrayList<String>();
        for(int i = 0; i < optionsets.size(); i++){
            setsNames.add(optionsets.get(i).getName());
        }
        return setsNames;
    }
    public void setOptionsets(List<OptionSet> optionsets){
        this.optionsets = optionsets;
    }

    public synchronized void setOption(int setIndex, String optName, float optPrice){
        //optSets[i] = new OptionSet(setName);
        optionsets.get(setIndex).setOption(optName,optPrice);
    }
    public synchronized void setOption(String setName, String optName, float optPrice){
        optionsets.get(findOptionsetByName(setName)).setOption(optName, optPrice);
    }

    public ArrayList<String> getOptionsNames(String setName){
        ArrayList<String> optionsNames = new ArrayList<>();
        int setIndex = findOptionsetByName(setName);
        for(int i = 0; i < getOptionset(setIndex).getOptions().size(); i++){
            optionsNames.add(getOptionset(setIndex).getOption(i).getName());
        }
        return optionsNames;
    }

    public String getChoice(String setName){
        //int i = findOptionsetByName(setName);
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

    public void makeChoice(){
        boolean finish = false;

        while(!finish){
            //display all optionset names
            for(OptionSet each : optionsets){
                System.out.println(each.getName());
            }
            //receive user's optionset selection
            Scanner userInput = new Scanner(System.in);
            System.out.println("Choose option set: ");
            String optionsetName = userInput.nextLine();

            //display option set's available choice
            OptionSet chosenOptionset = optionsets.get(findOptionsetByName(optionsetName));
            for(OptionSet.Option each : chosenOptionset.getOptions()){
                System.out.println(each.getName() + "--" + each.getPrice() );
            }

            //receive user's option value and save it
            System.out.println("Choose option: ");
            String optionName = userInput.nextLine();
            setChoice(optionsetName, optionName);
            System.out.println("option choice saved");

            System.out.println("Are you done?");
            if(userInput.nextLine().equals("yes")){ finish = true;}
        }

        System.out.println("configuration over");

    }
}
