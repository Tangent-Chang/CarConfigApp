package database;

import model.Automobile;

import java.sql.*;
import java.util.ArrayList;
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
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int findAuto(Automobile auto){
        int auto_id = 0;
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("FIND_AUTOMOBILE"));
            stmt.setString(1, auto.getMaker());
            stmt.setString(2, auto.getModelName());
            stmt.setFloat(3, auto.getBasePrice());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            auto_id = rs.getInt("auto_id");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return auto_id;
    }
    public void updateAuto(int auto_id, Automobile auto){
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("UPDATE_AUTOMOBILE"));
            stmt.setString(1, auto.getMaker());
            stmt.setString(2, auto.getModelName());
            stmt.setFloat(3, auto.getBasePrice());
            stmt.setFloat(4, auto_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteAuto(Automobile auto){
        try{
            ImplOptionset sqlOptionset = new ImplOptionset();
            ImplOption sqlOption = new ImplOption();
            int auto_id = findAuto(auto);
            ArrayList<Integer> set_ids = sqlOptionset.findOptionsets(auto_id);

            for(int i = 0; i<set_ids.size(); i++){
                sqlOption.deleteOptions(set_ids.get(i));
            }
            sqlOptionset.deleteOptionsets(auto_id);

            stmt = conn.prepareStatement(propSQL.getProperty("DELETE_AUTOMOBILE"));
            stmt.setInt(1, auto_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
