package server;


import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public interface IAutoServer {
    public boolean buildWithProperty(Properties properObj);
    public ArrayList<String> getModelList();
    public void sendSelectedAuto(ObjectOutputStream out, String modelName);
}
