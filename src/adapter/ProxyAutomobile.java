package adapter;

import exception.AutoException;
import model.*;
import scale.EditOptions;
import scale.FunctionCode;
import util.FileIO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Tangent Chang on 6/20/15.
 */
public abstract class ProxyAutomobile {
    private static Fleet fleetObj = new Fleet();

    public Fleet getFleet(){ return fleetObj;}

    public void buildAuto(String fileName, int fileType){
        Automobile autoObj;
        try {
            switch(fileType){
                case 1:  //previous text file
                    autoObj = FileIO.buildAutoObject(fileName);
                    fleetObj.setAuto(autoObj.getModelName(), autoObj);
                    break;
                case 2:  //properties file
                    autoObj = FileIO.buildWithProperty(fileName);
                    fleetObj.setAuto(autoObj.getModelName(), autoObj);
                    break;
            }
        }
        catch (AutoException e){
            e.printException();
            e.writeException();
        }
    }
    public void printAuto(String modelName){
        fleetObj.getAuto(fleetObj.findAuto(modelName)).print();
    }
    public void updateOptionSetName(String modelName, String setName, String newName){
        fleetObj.getAuto(fleetObj.findAuto(modelName)).updateOptionset(setName, newName);
    }
    public void updateOptionPrice(String modelName, String setName, String optName, float newPrice){
        fleetObj.getAuto(fleetObj.findAuto(modelName)).updateOption(setName, optName, optName, newPrice);
    }
    public void fixException(int errorNo){
        AutoException e = new AutoException();
        //e.fix(errorNo, fleetObj.getAuto(modelName));
    }

    public void editOptions(String modelName, FunctionCode func, String[] args){
        String k = fleetObj.findAuto(modelName);
        EditOptions edit = new EditOptions(func, args, fleetObj.getAuto(k));
        edit.run();
        //edit.editOptions(fleetObj.getAuto(k), func, args);
    }

    public boolean buildWithProperty(Properties properObj){
        Automobile autoObj = FileIO.parseProperties(properObj);
        fleetObj.setAuto(autoObj.getModelName(), autoObj);
        System.out.println("build auto obj and add into fleet in ProxyAutomobile");
        return true;
    }

    public ArrayList<String> getModelList(){
        ArrayList<String> nameList = new ArrayList<>();

        Iterator iter = fleetObj.getAutos().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Automobile a = (Automobile) entry.getValue();
            nameList.add(a.getModelName());
        }
        return nameList;
    }
    public void sendSelectedAuto(ObjectOutputStream out, String modelName){
        String k = fleetObj.findAuto(modelName);
        if(k != null){
            Automobile a = fleetObj.getAuto(k);
            try{
                out.writeObject(a);
            }
            catch(IOException e){
                System.out.print("Error: " + e);
                System.exit(1);
            }
        }

    }
}
