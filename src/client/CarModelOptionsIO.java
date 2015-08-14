package client;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by Tangnet Chang on 7/10/15.
 */
public class CarModelOptionsIO {
    public void uploadProperty(Socket client, String fileName){
        Properties properObj= new Properties();
        try {
            FileInputStream in = new FileInputStream(fileName);
            properObj.load(in);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(properObj);

        } catch (Exception e) {
            System.out.print("Error: " + e);
            System.exit(1);
        }

    }
}
