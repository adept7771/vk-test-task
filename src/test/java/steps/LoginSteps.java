package steps;

import io.qameta.allure.Step;
import pagesAndElements.LeftMenuGlobal;
import pagesAndElements.VkIdPage;
import pagesAndElements.LoginPage;

import static com.codeborne.selenide.Condition.visible;

public class LoginSteps {

    @Step("Шаг - логин с именем {0} и паролем {1}")
    public void login(String userName, String password){
        // Для ускорения прохождения логина можно попробовать куки вытаскивать и подпихивать в
        // другие тесты. Но сработает ли - это не точно.
        commonSteps.openSite();
        loginPage.phoneOrEmailInput.shouldBe(visible).val(userName);
        loginPage.enterButton.shouldBe(visible).click();
        vkIdPage.passwordInput.shouldBe(visible).val(password);
        vkIdPage.continueLoginButton.shouldBe(visible).click();
        leftMenuGlobal.blogLink.shouldBe(visible);
    }

    private LeftMenuGlobal leftMenuGlobal = new LeftMenuGlobal();
    private LoginPage loginPage = new LoginPage();
    private VkIdPage vkIdPage = new VkIdPage();

    CommonSteps commonSteps = new CommonSteps();
}
