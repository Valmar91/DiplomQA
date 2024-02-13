package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }
    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }
//        if(System.getProperty("db.url").contains("postgresql")) {
//            return DriverManager.getConnection(System.getProperty("db.url"), "root", "rootroot");
//        }
//        if (System.getProperty("db.url").contains("mysql")) {
//            return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
//        }
//        throw new SQLException("No DATABASE");
//    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static DataHelper.PaymentSpreadsheet getPaymentSpreadsheet() {
        var codSQL = "SELECT * FROM payment_entity WHERE created = (SELECT MAX(created) FROM payment_entity)";
        var connection = getConn();
        try (connection) {
            return QUERY_RUNNER.query(connection, codSQL, new BeanHandler<>(DataHelper.PaymentSpreadsheet.class));
        }
    }

    @SneakyThrows
    public static DataHelper.CreditSpreadsheet getCreditSpreadsheet() {
        var codSQL = "SELECT * FROM credit_request_entity WHERE created = (SELECT MAX(created) FROM credit_request_entity)";
        var connection = getConn();
        try (connection) {
            return QUERY_RUNNER.query(connection, codSQL, new BeanHandler<>(DataHelper.CreditSpreadsheet.class));
        }
    }

    @SneakyThrows
    public static DataHelper.OrderSpreadsheet getOrderSpreadsheet() {
        var codSQL = "SELECT * FROM order_entity WHERE created = (SELECT MAX(created) FROM order_entity)";
        var connection = getConn();
        try (connection) {
            return QUERY_RUNNER.query(connection, codSQL, new BeanHandler<>(DataHelper.OrderSpreadsheet.class));
        }
    }
}
