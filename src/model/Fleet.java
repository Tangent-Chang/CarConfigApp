package model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tangent Chang on 6/24/15.
 */
public class Fleet {
    private Map<String, Automobile> autos;

    public Fleet(){
        autos = new LinkedHashMap<String, Automobile>();
    }

    public Map<String, Automobile> getAutos(){
        return autos;
    }
    public Automobile getAuto(String key){
        return autos.get(key);
    }
    public void setAuto(String key){
        autos.put(key, new Automobile());
    }
    public void setAuto(String key, Automobile auto){
        autos.put(key, auto);
    }

    public void updateAutoModelName(String key, String newModelName){
        autos.get(key).setModelName(newModelName);
    }
    public void updateAutoMaker(String key, String newMaker){
        autos.get(key).setMaker(newMaker);
    }
    public void updateAutoBasePrice(String key, float newPrice){
        autos.get(key).setBasePrice(newPrice);
    }
    /*public void updateAuto(String key, String maker, String modelName, float price){
        autos.get(key).setMaker(maker);
        autos.get(key).setModelName(modelName);
        autos.get(key).setBasePrice(price);
    }*/

    public String findAuto(String modelName){
        String key = null;
        //Boolean flag = false;

        //Map map = new HashMap();
        Iterator iter = autos.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Automobile e = (Automobile) entry.getValue();
            if(e.getModelName().equals(modelName)){
                key = (String) entry.getKey();
                break;
            }
        }
        return key;
    }

    public void deleteAuto(String index){
        autos.remove(index);
    }
}
