package database;

import model.Automobile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Tangent Chang on 8/17/15.
 */
public class ImplAutomobile extends ImplBase{

    public ImplAutomobile(){
        super();
    }

    public void addAuto(Automobile auto){

        try{
            String test = prop.getProperty("ADD_AUTOMOBILE");
            if(conn == null){
                System.out.println("conn is null");
            }
            else{
                stmt = conn.prepareStatement(test);
            }


            stmt.setString(1, auto.getMaker());
            stmt.setString(2, auto.getModelName());
            stmt.setFloat(3, auto.getBasePrice());

            stmt.executeUpdate();
            stmt.clearParameters();

            /*ImplOptionset sqlOptionset = new ImplOptionset();
            ImplOption sqlOption = new ImplOption();
            auto.getOptionsets();
            auto.getOptions(setIndex);*/
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
