package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Tangent Chang on 8/16/15.
 */
public class DBUtil {
    private Properties prop;
    private String url;
    private String user;
    private String password;
    private static Connection conn;

    public DBUtil() throws IOException, ClassNotFoundException{
        this("jdbc.properties");
    }

    public DBUtil(String configFile) throws IOException, ClassNotFoundException{
        prop = new Properties();
        prop.load(new FileInputStream(configFile));

        url = prop.getProperty("CarConfigApp.url");
        user = prop.getProperty("CarConfigApp.user");
        password = prop.getProperty("CarConfigApp.password");

        Class.forName(prop.getProperty("CarConfigApp.driver"));
    }

    public Connection getConnection() throws SQLException{
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
    public void closeConnection(Connection conn) throws SQLException{
        conn.close();
    }
}
