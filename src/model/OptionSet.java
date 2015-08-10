package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tangent Chang on 6/14/15.
 */
class OptionSet implements Serializable{

    private List<Option> options;
    private String name;

    //protected OptionSet(){}
    protected OptionSet(String n){
        name = n;
        options = new ArrayList<Option>();
    }
    /*protected OptionSet(String n, String[] names, float[] prices){
        name = n;
        options = new ArrayList<Option>();
        for(int i = 0; i < names.length; i++) {
            options.add(new Option(names[i], prices[i]));
        }
    }*/

    protected String getName(){return name;}
    protected void setName(String n){ name = n;}

    protected List<Option> getOptions(){return options;}
    //protected void setOpts(int size){ opts = new Option[size];}
    /*protected void setOpts(String[] names, float[] prices){
        for(int i=0; i<names.length; i++){
            options[i] = new Option(names[i], prices[i]);
        }
    }*/
    protected Option getOption(int i){return options.get(i);}
    protected void setOption(String n, float p){
        options.add(new Option(n, p));
    }

    protected int findOptionByName(String n){
        for(int i=0;i<options.size();i++){
            if(options.get(i).getName().equals(n)){return i;}
        }
        return -1;
    }

    protected void deleteOption(int i){
        options.remove(i);
    }
    protected void deleteOption(String optName){
        int i = findOptionByName(optName);
        options.remove(i);
    }

    /*protected void updateOption(int i, String n){
        options.get(i).setName(n);
        //options.get(i).setPrice(p);
    }
    protected void updateOption(int i, float p){
        //options.get(i).setName(n);
        options.get(i).setPrice(p);
    }*/
    protected void updateOption(String optName, String n, float p){
        int i = findOptionByName(optName);
        options.get(i).setName(n);
        options.get(i).setPrice(p);
    }
    /*protected void updateOptions(String[] names, float[] prices){
        for(int i=0; i<names.length; i++){
            options.get(i).setName(names[i]);
            options.get(i).setPrice(prices[i]);
        }
    }*/

    class Option implements Serializable{
        private String name; //this is value too. e.g. red
        private float price;

        protected Option(String n, float p){
            name = n;
            price = p;
        }

        protected String getName(){return name;}
        protected void setName(String n){ name = n;}
        protected float getPrice(){return price;}
        protected void setPrice(float p){price = p;}
        //protected String getValue(){return value;}
        //protected void setValue(){}

    }


}

