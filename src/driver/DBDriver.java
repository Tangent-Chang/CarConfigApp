package driver;

import adapter.BuildAuto;
import database.DBUtil;
import model.Fleet;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Tangent Chang on 8/16/15.
 */
public class DBDriver {
    public static void main(String[] args){
        //try{
            BuildAuto build = new BuildAuto();
            build.buildAuto("car1.txt",1);
            build.buildAuto("car2.txt",1);
            build.buildAuto("car.properties",2);

            Fleet flt = build.getFleet();

            flt.updateAutoBasePrice("Focus Wagon ZTW", 15800);
            flt.updateAutoMaker("Lexus 689", "ZIP");
            flt.updateAutoModelName("Prius","Hybrid");

            flt.deleteAuto("Focus Wagon ZTW");


            /*DBUtil dbSource = new DBUtil();
            Connection conn = dbSource.getConnection();

            if(!conn.isClosed()){
                System.out.println("資料庫連接已開啟…");
            }

            Statement stmt = conn.createStatement();
            Properties prop = new Properties();
            prop.load(new FileInputStream("SQL.properties"));

            stmt.executeUpdate(prop.getProperty("DROP_TABLES"));
            stmt.executeUpdate(prop.getProperty("CREATE_AUTOMOBILE"));
            stmt.executeUpdate(prop.getProperty("CREATE_OPTIONSET"));
            stmt.executeUpdate(prop.getProperty("CREATE_OPTION"));
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }


            dbSource.closeConnection(conn);

            if(conn.isClosed()){
                System.out.println("資料庫連接已關閉…");*//*
            }*/
        //}
        /*catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
