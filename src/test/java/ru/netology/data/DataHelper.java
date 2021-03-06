package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfoCorrectPassword() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWrongPassword() {
        return new AuthInfo("vasya", "123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) throws SQLException {
        String id = "";
        String code = "";
        val idSQL = "SELECT id FROM users WHERE login='" + authInfo.login + "'";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            id = (String) runner.query(conn, idSQL, new ScalarHandler<>());
            val codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes WHERE user_id='" + id + "')";
            code = (String) runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException a) {
            a.printStackTrace();
        }
        return new VerificationCode(code);
    }

    public static VerificationCode getWrongVerificationCode() {
        return new VerificationCode("123");
    }
}
