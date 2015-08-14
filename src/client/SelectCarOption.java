package client;

import model.Automobile;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Tangent Chang on 7/10/15.
 */
public class SelectCarOption {
    private Automobile auto;
    public void retrieveCar(Socket sock){
        try{
            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            auto = (Automobile) ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println("IOException :" + e.toString());
        }
    }

    public void makeChoice(){
        boolean finish = false;

        while(!finish){
            //display all optionset names

            for(int i=0; i< auto.getOptionsets().size(); i++){
                String setName = auto.getOptionsetName(i);
                System.out.println(setName);
            }

            //receive user's optionset selection
            Scanner userInput = new Scanner(System.in);
            System.out.println("Choose option set: ");
            String optionsetName = userInput.nextLine();

            //display option set's available choice
            int setIndex = auto.findOptionsetByName(optionsetName);
            for(int i = 0; i < auto.getOptions(setIndex).size(); i++){
                String optName = auto.getOptionName(optionsetName, i);
                float price = auto.getOptionPrice(optionsetName, i);
                System.out.println(optName + " -- " + price );
            }

            //receive user's option value and save it
            System.out.println("Choose option: ");
            String optionName = userInput.nextLine();
            auto.setChoice(optionsetName, optionName);
            System.out.println("option choice saved");

            System.out.println("Are you done?");
            if(userInput.nextLine().equals("yes")){ finish = true;}
        }

        System.out.println("configuration over");

    }
}
