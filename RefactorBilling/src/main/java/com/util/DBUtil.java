//package com.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBUtil {
//    private static final String URL      = "jdbc:mysql://localhost:3307/saasant_billing";
//    private static final String USER     = "root";
//    private static final String PASSWORD = "12345";
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("MySQL driver not found", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}

package com.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    // 1) Single shared instance
    private static volatile DBUtil instance;

    // 2) DataSource for connection pooling (you can swap out for HikariCP, etc.)
    private final DataSource dataSource;

    // 3) Private constructor: initialize the DataSource
    private DBUtil() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("jdbc:mysql://localhost:3307/saasant_billing");
        ds.setUser("root");
        ds.setPassword("12345");
        this.dataSource = ds;
    }

    // 4) Double‐checked locking for lazy, thread-safe init
    public static DBUtil getInstance() {
        if (instance == null) {
            synchronized (DBUtil.class) {
                if (instance == null) {
                    instance = new DBUtil();
                }
            }
        }
        return instance;
    }

    // 5) Expose Connection‐getter
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
