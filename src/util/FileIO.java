package util;

import exception.AutoException;
import exception.ModelError;
import model.Automobile;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Properties;

/**
 * Created by Tangent Chang on 6/14/15.
 */
public class FileIO {
    public static Automobile buildAutoObject(String fileName) throws AutoException{
        Automobile model = new Automobile();
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(file);
            boolean eof = false;

            String line;
            for(int i=0; i<3; i++){
                line = buff.readLine();
                if(line == null){eof = true;}
                else{
                    String[] array = line.split(": ");
                    try{
                        if(array.length == 2){
                            if(i == 0){
                                model.setModelName(array[1]);
                            }
                            else if(i == 1){
                                try {
                                    float basePrice = NumberFormat.getNumberInstance(java.util.Locale.US).parse(array[1]).floatValue();
                                    model.setBasePrice(basePrice);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(i == 2){
                                model.setMaker(array[1]);
                            }
                        }
                        else{
                            if(i == 0){ throw new AutoException(ModelError.MISSING_MODEL_NAME);}
                            else if(i == 1){ throw new AutoException(ModelError.MISSING_MODEL_PRICE);}
                        }
                    }
                    catch(AutoException e){
                        e.fix(e.getErrorCode().getNumber(), model);
                    }
                }
            }

            int i = 0; //number of line
            while(!eof){
                line = buff.readLine();
                if (line == null) {
                    eof = true;
                }
                else{
                    String[] optionSet = line.split(": "); //setName + options
                    try {
                        if (optionSet.length != 2 || optionSet[0].isEmpty() || optionSet[1].isEmpty()) {
                            throw new AutoException(ModelError.MISSING_OPTIONSET_DATA);
                        } else {
                            String[] options = optionSet[1].split("; "); //(optionName + price)*n
                            model.setOptionset(optionSet[0]);
                            for (int j = 0; j < options.length; j++) {
                                String[] option = options[j].split(", "); //optionName + price
                                if (option.length < 2) {
                                    throw new AutoException(ModelError.MISSING_OPTION_DATA);
                                } else {
                                    try {
                                        float price = NumberFormat.getNumberInstance(java.util.Locale.US).parse(option[1]).floatValue();
                                        model.setOption(i, option[0], price);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    }
                    catch(AutoException e){
                        e.fix(e.getErrorCode().getNumber(), model);
                    }

                }
                i++;
            }
            buff.close();
        }
        catch (IOException e) {
            System.out.println("Error -- " + e.toString());
        }
        return model;
    }

    public static Automobile buildWithProperty(String fileName){
        Automobile autoObj = null;
        Properties properObj= new Properties();

        try{
            FileInputStream in = new FileInputStream(fileName);
            properObj.load(in);

            /*autoObj.setModelName(properObj.getProperty("CarModel"));
            properObj.remove("CarModel");
            autoObj.setMaker(properObj.getProperty("CarMake"));
            properObj.remove("CarMake");
            autoObj.setBasePrice(Float.parseFloat(properObj.getProperty("CarPrice")));
            properObj.remove("CarPrice");

            int i=1;
            String key = "Option";
            while(!properObj.isEmpty()){
                String optionsetName = properObj.getProperty(key+i);
                autoObj.setOptionset(optionsetName);
                properObj.remove(key+i);

                int j=1;
                while(properObj.containsKey(key + i + "Value" + j)){
                    float optionPrice = 0;
                    String optionName = properObj.getProperty(key+i+"Value"+j);
                    properObj.remove(key+i+"Value"+j);
                    if(properObj.containsKey(key+i+"Value"+j+"price")){
                        optionPrice = Float.parseFloat(properObj.getProperty(key+i+"Value"+j+"price"));
                        properObj.remove(key+i+"Value"+j+"price");
                    }
                    autoObj.setOption(optionsetName, optionName, optionPrice);
                    j++;
                }
                i++;
            }*/
            autoObj = parseProperties(properObj);
            in.close();
        }
        catch (IOException e) {
            System.out.println("Error -- " + e.toString());
        }
        return autoObj;
    }

    public static Automobile parseProperties(Properties properObj){
        Automobile autoObj = new Automobile();

        autoObj.setModelName(properObj.getProperty("CarModel"));
        properObj.remove("CarModel");
        autoObj.setMaker(properObj.getProperty("CarMake"));
        properObj.remove("CarMake");
        autoObj.setBasePrice(Float.parseFloat(properObj.getProperty("CarPrice")));
        properObj.remove("CarPrice");

        int i=1;
        String key = "Option";
        while(!properObj.isEmpty()) {
            String optionsetName = properObj.getProperty(key + i);
            autoObj.setOptionset(optionsetName);
            properObj.remove(key + i);

            int j = 1;
            while (properObj.containsKey(key + i + "Value" + j)) {
                float optionPrice = 0;
                String optionName = properObj.getProperty(key + i + "Value" + j);
                properObj.remove(key + i + "Value" + j);
                if (properObj.containsKey(key + i + "Value" + j + "price")) {
                    optionPrice = Float.parseFloat(properObj.getProperty(key + i + "Value" + j + "price"));
                    properObj.remove(key + i + "Value" + j + "price");
                }
                autoObj.setOption(optionsetName, optionName, optionPrice);
                j++;
            }
            i++;
        }
        return autoObj;
    }

    public static void serializeAuto(Automobile car){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("car.dat"));
            out.writeObject(car);
            out.close();
        }
        catch(Exception e){
            System.out.print("Error: " + e);
            System.exit(1);
        }
    }

    public static Automobile deserializeAuto(){
        Automobile newCar = null;
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.dat"));
            newCar = (Automobile) in.readObject();
        }
        catch(Exception e){
            System.out.print("Error: " + e);
            System.exit(1);
        }
        return newCar;
    }
}
