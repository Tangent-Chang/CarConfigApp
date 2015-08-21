package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Tangent Chang on 8/17/15.
 */
public class ImplOption extends ImplBase{
    private int optionId;
    public int addOption(int setId, String optionName, float optionPrice){
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("ADD_OPTION"), Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, setId);
            stmt.setString(2, optionName);
            stmt.setFloat(3, optionPrice);

            stmt.executeUpdate();
            //stmt.clearParameters();

            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()){
                optionId = key.getInt(1);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return optionId;
    }
    public void updateOption(){}
    public void deleteOptions(int set_id){
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("DELETE_OPTIONS"));
            stmt.setInt(1, set_id);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
