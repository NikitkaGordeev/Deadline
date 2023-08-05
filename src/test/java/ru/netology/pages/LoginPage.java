package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id=login] input");
    private final SelenideElement password = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public VerifyPage validLogin(DataHelper.AuthUser user) {
        login.setValue(user.getLogin());
        password.setValue(user.getPassword());
        loginButton.click();
        return new VerifyPage();
    }

    public void clean() {
        login.doubleClick().sendKeys(Keys.BACK_SPACE);
        password.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void getError() {
        error.shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
        error.shouldBe(Condition.visible);
    }

}
