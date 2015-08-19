package database;

import model.Automobile;

import java.sql.*;
import java.util.Properties;


/**
 * Created by Tangent Chang on 8/17/15.
 */
public class ImplAutomobile extends ImplBase{


    public void addAuto(Automobile auto){

        try{
            stmt = conn.prepareStatement(propSQL.getProperty("ADD_AUTOMOBILE"), Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, auto.getMaker());
            stmt.setString(2, auto.getModelName());
            stmt.setFloat(3, auto.getBasePrice());

            stmt.executeUpdate();
            //stmt.clearParameters();

            ImplOptionset sqlOptionset = new ImplOptionset();
            ImplOption sqlOption = new ImplOption();

            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()){
                for(int i = 0; i<auto.getOptionsets().size(); i++){
                    String setName = auto.getOptionsetName(i);
                    int setId = sqlOptionset.addOptionset(key.getInt(1), setName);

                    for(int j = 0; j<auto.getOptions(i).size(); j++){
                        String optionName = auto.getOptionName(setName, j);
                        float optionPrice = auto.getOptionPrice(setName, j);
                        sqlOption.addOption(setId, optionName, optionPrice);
                    }
                }
            }
            else{

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    /*public void updateAuto(){
        try{}
        catch (SQLException e){
            e.printStackTrace();
        }
    }*/
    /*public void deleteAuto(){
        try{
            ImplOptionset sqlOptionset = new ImplOptionset();
            ImplOption sqlOption = new ImplOption();
            sqlOption.deleteOption();
            sqlOptionset.deleteOptionset();

            deleteAuto();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }*/
}
