package database;

import model.Automobile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Tangent Chang on 8/17/15.
 */
public class ImplOptionset extends ImplBase {
    private int setId;

    public int addOptionset(int autoId, String setName){
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("ADD_OPTIONSET"), Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, autoId);
            stmt.setString(2, setName);
            stmt.executeUpdate();
            //stmt.clearParameters();

            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()){
                setId = key.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return setId;
    }
    public void updateOptionset(){}
    public void deleteOptionset(){}
}
