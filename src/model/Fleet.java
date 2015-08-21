package model;

import database.ImplAutomobile;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tangent Chang on 6/24/15.
 */
public class Fleet {
    private Map<String, Automobile> autos;
    private ImplAutomobile sqlAuto = new ImplAutomobile();


    public Fleet(){
        autos = new LinkedHashMap<String, Automobile>();
    }

    public Map<String, Automobile> getAutos(){
        return autos;
    }
    public Automobile getAuto(String key){
        return autos.get(key);
    }
    /*public void setAuto(String key){
        autos.put(key, new Automobile());
        sqlAuto.addAuto();
    }*/
    public void setAuto(String key, Automobile auto){
        autos.put(key, auto);
        sqlAuto.addAuto(auto);
    }

    public void updateAutoModelName(String key, String newModelName){
        int auto_id = sqlAuto.findAuto(autos.get(key));
        autos.get(key).setModelName(newModelName);
        sqlAuto.updateAuto(auto_id, autos.get(key));
    }
    public void updateAutoMaker(String key, String newMaker){
        int auto_id = sqlAuto.findAuto(autos.get(key));
        autos.get(key).setMaker(newMaker);
        sqlAuto.updateAuto(auto_id, autos.get(key));
    }
    public void updateAutoBasePrice(String key, float newPrice){
        int auto_id = sqlAuto.findAuto(autos.get(key));
        autos.get(key).setBasePrice(newPrice);
        sqlAuto.updateAuto(auto_id, autos.get(key));
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
        sqlAuto.deleteAuto(autos.get(index));
        autos.remove(index);
    }
}
