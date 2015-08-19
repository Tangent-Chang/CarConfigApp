package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Tangent Chang on 8/17/15.
 */
public class ImplBase {
    protected Connection conn = null;
    protected PreparedStatement stmt = null;
    protected Properties prop = null;

    public ImplBase(){
        try{
            prop = new Properties();
            prop.load(new FileInputStream("SQL.properties"));
            setConnection();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setConnection(){

        try{
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
