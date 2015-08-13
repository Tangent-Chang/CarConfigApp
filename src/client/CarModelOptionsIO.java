package client;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Tangnet Chang on 7/10/15.
 */
public class CarModelOptionsIO {

    //public void uploadProperty(ObjectOutputStream oos, ObjectInputStream ois){
    public void uploadProperty(Socket client){
        Properties properObj= new Properties();
        //InetSocketAddress isa = new InetSocketAddress(this.serverAddress, this.serverPort);
        try {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter file name: ");
            String fileName = userInput.nextLine();

            FileInputStream in = new FileInputStream(fileName);
            properObj.load(in);
            //CarConfigApp_client.client.connect(isa, 10000);
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(properObj);

            client.shutdownOutput(); /* important */

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            //ObjectInputStream ois = new ObjectInputStream(is);
            //ois.skip(Long.MAX_VALUE);
            String confirmation = (String) ois.readObject();
            System.out.println("from server : " + confirmation);

            //in.close();
            //out.close();

        } catch (Exception e) {
            //System.out.println("Socket連線有問題 !");
            System.out.print("Error: " + e);
            System.exit(1);
        }

    }
}
