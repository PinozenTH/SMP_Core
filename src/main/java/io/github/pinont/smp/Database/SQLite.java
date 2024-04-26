package io.github.pinont.smp.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.pinont.smp.Core.plugin;

public class SQLite {

    static Connection connection;

    public static Connection init() {
        String db_path = "jdbc:sqlite:plugins/SMP/" + plugin.getConfig().getString("sqlite.file_name") + ".db";
        try {
            return connection = DriverManager.getConnection(db_path);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
