/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

/**
 *
 * @author Nina
 */
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

public class DataSourceSingleton {
    
    private static DataSource instance;
    
    private static final String PWD = "sql";
    private static final String DB = "WebShop";
    private static final String USER = "sa";
    private static final String SERVER = "localhost";

    private DataSourceSingleton() {
    }

    public static DataSource getInstance() {
        if (instance==null) {
            instance=createInstance();
        }
        return instance;
    }
    
    private static DataSource createInstance() {
        SQLServerDataSource dataSource=new SQLServerDataSource();
        
        dataSource.setServerName(SERVER);
        dataSource.setUser(USER);
        dataSource.setDatabaseName(DB);
        dataSource.setPassword(PWD);
        
        return dataSource;
    }
}
