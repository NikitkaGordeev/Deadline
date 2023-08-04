package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.management.Query;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "user", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerifyCode getVerifyCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            var result = runner.query(connection, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerifyCode(result);
        }
    }

    @SneakyThrows
    public static void cleanDB() {
        var connection = getConnection();
        runner.update(connection, "DELETE FROM auth_codes");
        runner.update(connection, "DELETE FROM card_transactions");
        runner.update(connection, "DELETE FROM cards");
        runner.update(connection, "DELETE FROM users");
    }
}

