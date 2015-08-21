package database;

import model.Automobile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    public ArrayList<Integer> findOptionsets(int auto_id){
        ArrayList<Integer> set_ids = new ArrayList<>();
        //int[] set_ids = new int[];
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("FIND_OPTIONSET"));
            stmt.setInt(1, auto_id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                set_ids.add(rs.getInt("set_id"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return set_ids;
    }
    public void deleteOptionsets(int auto_id){
        try{
            stmt = conn.prepareStatement(propSQL.getProperty("DELETE_OPTIONSETS"));
            stmt.setInt(1, auto_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
