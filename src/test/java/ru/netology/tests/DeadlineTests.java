package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDB;


public class DeadlineTests {

    @AfterAll
    static void cleaner() {
        cleanDB();
    }

    @Test
    void successLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataHelper.getAuthUser();
        var verifyPage = loginPage.validLogin(authUser);
        verifyPage.verifyPageVisibility();
        var verifyCode = SQLHelper.getVerifyCode();
        verifyPage.validVerify(verifyCode.getCode());
    }

    @Test
    void randomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    void invalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataHelper.AuthUser(DataHelper.getRandomUser().getLogin(),
                DataHelper.getAuthUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getError();
    }

    @Test
    void invalidPass() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getError();
    }

    @Test
    void invalidVerifyCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataHelper.getAuthUser();
        var verifyPage = loginPage.validLogin(authUser);
        verifyPage.verifyPageVisibility();
        var verifyCode = DataHelper.getRandomVerifyCode().getCode();
        verifyPage.verify(verifyCode);
        verifyPage.getError();
    }
}