package client;

import model.Automobile;
//import model.OptionSet;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tangent Chang on 7/10/15.
 */
public class SelectCarOption {

    //public void configureCar(ObjectOutputStream oos, ObjectInputStream ois){
    public void configureCar(Socket client, ObjectOutputStream oos){
        Scanner userInput = new Scanner(System.in);

        try{
            //receive model list from server
            InputStream is = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ArrayList<String> modelList = (ArrayList<String>) ois.readObject();
            //display list
            for(String each : modelList){
                System.out.println(each);
            }

            //send CarConfigApp_server.server selected CarConfigApp_client.CarConfigApp_server.model name
            System.out.println("enter model name: ");
            String selectedAuto = userInput.nextLine();
            //OutputStream os = CarConfigApp_client.client.getOutputStream();
            //ObjectOutputStream out = new ObjectOutputStream(os);
            oos.writeObject(selectedAuto);
            System.out.println("client receive " + selectedAuto);

            //receive that auto obj
            Automobile chosenAuto = (Automobile) ois.readObject();
            System.out.println("client receive auto obj: " + chosenAuto.getModelName());

            //configure: receive and display auto's options, then user make choice (send decision to CarConfigApp_server.server), and repeat
            makeChoice(chosenAuto);
        }
        catch(Exception e)
        {
            //System.out.println("Socket連線有問題 !" );
            System.out.println("IOException :" + e.toString());
        }

    }

    public void makeChoice(Automobile chosenAuto){
        boolean finish = false;

        while(!finish){
            //display all optionset names

            for(int i=0; i< chosenAuto.getOptionsets().size(); i++){
                String setName = chosenAuto.getOptionsetName(i);
                System.out.println(setName);
            }

            //receive user's optionset selection
            Scanner userInput = new Scanner(System.in);
            System.out.println("Choose option set: ");
            String optionsetName = userInput.nextLine();

            //display option set's available choice
            int setIndex = chosenAuto.findOptionsetByName(optionsetName);
            for(int i = 0; i < chosenAuto.getOptions(setIndex).size(); i++){
                String optName = chosenAuto.getOptionName(optionsetName, i);
                float price = chosenAuto.getOptionPrice(optionsetName, i);
                System.out.println(optName + " -- " + price );
            }

            //receive user's option value and save it
            System.out.println("Choose option: ");
            String optionName = userInput.nextLine();
            chosenAuto.setChoice(optionsetName, optionName);
            System.out.println("option choice saved");

            System.out.println("Are you done?");
            if(userInput.nextLine().equals("yes")){ finish = true;}
        }

        System.out.println("configuration over");

    }
}
