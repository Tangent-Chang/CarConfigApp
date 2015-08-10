package scale;

import model.Automobile;

import java.text.NumberFormat;
import java.text.ParseException;


/**
 * Created by Tangent Chang on 6/26/15.
 */
public class EditOptions implements Runnable {

    private FunctionCode func;
    private String[] args;
    private Automobile auto;

    public EditOptions(FunctionCode function, String[] arguments, Automobile a){
        func = function;
        args = arguments;
        auto = a;
    }

    public void run(){
        try {
            //args = set name, option name, new option name, new option price
            String optionsetName = args[0];
            String optionName = args[1];
            String newOptionName = args[2];
            float newOptionPrice;
            if(!args[3].isEmpty()){newOptionPrice = NumberFormat.getNumberInstance(java.util.Locale.US).parse(args[3]).floatValue();}
            else{ newOptionPrice = 0;}

            int i, j;
            switch(func.getNo()){
                case 01:
                    i = auto.findOptionsetByName(optionsetName);
                    //j = auto.findOptionByName(i,optionName);
                    auto.updateOption(optionsetName, optionName, newOptionName, newOptionPrice);
                    break;
                case 03:
                    i = auto.findOptionsetByName(optionsetName);
                    //j = auto.findOptionByName(i,optionName);
                    auto.updateOption(optionsetName, optionName, newOptionName, newOptionPrice);
                    break;
                case 05:
                    auto.setOption(optionsetName,newOptionName, newOptionPrice);
                    break;
                case 07:
                    i = auto.findOptionsetByName(optionsetName);
                    j = auto.findOptionByName(i, optionName);
                    auto.deleteOption(i,j);
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
