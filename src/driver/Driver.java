package driver;

import adapter.BuildAuto;
import client.Client;
import server.Server;

/**
 * Created by Tangent Chang on 6/14/15.
 */
public class Driver {

        public static void main(String [] args) {
            BuildAuto testDriver = new BuildAuto();
            testDriver.buildAuto("car1.txt", 1);
            testDriver.buildAuto("car2.txt", 1);
            //testDriver.updateOptionPrice("haha", "Color", "Infra-Red Clearcoat", 100);
            //testDriver.updateOptionSetName("haha", "Color", "Color choice");
            //testDriver.printAuto("Focus Wagon ZTW");

            //instantiation of BuildAuto
            //Create two threads using EditOptions, which will modify the same CarConfigApp_client.CarConfigApp_server.model as LinkedHashMap instance of Automobile (static object) in proxyAutomotive class instance.

            //args = set name, option name, new option name, new option price
            /*String[] parameter1 = {"Color", "Liquid Grey Clearcoat Metallic", "Grey", ""};
            String[] parameter2 = {"Color", "Liquid Grey Clearcoat Metallic", "", "100"};
            Automobile auto = testDriver.getFleet().getAuto("Focus Wagon ZTW");
            Thread t1 = new Thread(new EditOptions(FunctionCode.EDIT_OPTION_NAME, parameter1, auto), "t1");
            Thread t2 = new Thread(new EditOptions(FunctionCode.EDIT_OPTION_PRICE, parameter2, auto), "t2");

            t2.start();
            t1.start();*/

            String[] argu = {};
            //Thread tServer = new Thread(new Server());
            //Thread tClient = new Thread(new Client());
            //tServer.start();
            Server.main(argu);
            System.out.println("server start in Driver");
            //tClient.start();

            Client.main(argu);
            System.out.println("client start in Driver");


        }
}
