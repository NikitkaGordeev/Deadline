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
    void SuccessLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataHelper.getAuthUser();
        var verifyPage = loginPage.validLogin(authUser);
        verifyPage.verifyPageVisibility();
        var verifyCode = SQLHelper.getVerifyCode();
        verifyPage.validVerify(verifyCode.getCode());
    }

    @Test
    void RandomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    void InvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataHelper.AuthUser(DataHelper.getRandomUser().getLogin(),
                DataHelper.getAuthUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getError();
    }

    @Test
    void InvalidPass() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getError();
    }

    @Test
    void InvalidVerifyCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataHelper.getAuthUser();
        var verifyPage = loginPage.validLogin(authUser);
        verifyPage.verifyPageVisibility();
        var verifyCode = DataHelper.getRandomVerifyCode().getCode();
        verifyPage.verify(verifyCode);
        verifyPage.getError();
    }

    @Test
    void PassThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUserOne = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserOne);
        loginPage.getError();
        loginPage.clean();
        clearBrowserCookies();
        var authUserTwo = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserTwo);
        loginPage.getError();
        loginPage.clean();
        clearBrowserCookies();
        var authUserThree = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserThree);
        loginPage.getBlock();
    }
}