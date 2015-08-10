package server;

import adapter.BuildAuto;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public class BuildCarModelOptions implements IAutoServer{
    static private BuildAuto auto = new BuildAuto();

    public boolean buildWithProperty(Properties propObj){
        if(auto.buildWithProperty(propObj)){
            return true;
        }
        return false;
    }

    public ArrayList<String> getModelList(){
        ArrayList<String> list = new ArrayList<String>();
        if(auto != null){
            list = auto.getModelList();
        }
        return list;
    }
    public void sendSelectedAuto(ObjectOutputStream out, String modelName){
        auto.sendSelectedAuto(out, modelName);
    }

}
