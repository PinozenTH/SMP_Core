package io.github.pinont.smp.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.pinont.smp.Core.plugin;

public class MySQL {
    public static Connection connection;

    public static Connection init() throws SQLException {
        String host = plugin.getConfig().getString("mysql.host");
        String port = plugin.getConfig().getString("mysql.port");
        String database = plugin.getConfig().getString("mysql.database");
        String username = plugin.getConfig().getString("mysql.username");
        String password = plugin.getConfig().getString("mysql.password");
        return connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" +  password + "&useSSL=true&verifyServerCertificate=false"
        );
    }

    public static Connection getConnection() {
        return connection;
    }

}
