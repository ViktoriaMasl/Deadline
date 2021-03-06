package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class Autotest {

    @BeforeEach
    void openSut() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldAuthorizationValidInfo() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoCorrectPassword();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.personalAccount();
    }

    @Test
    void shouldAuthorizationInvalidInfo() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoWrongPassword();
        loginPage.invalidPassword(authInfo);
    }

    @Test
    void shouldAuthorizationInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoCorrectPassword();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getWrongVerificationCode();
        verificationPage.invalidCode(verificationCode.getCode());
    }

    @Test
    void shouldBlockedPersonalAccount() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoCorrectPassword();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.blockedCode(DataHelper.getWrongVerificationCode().getCode());
    }
}
