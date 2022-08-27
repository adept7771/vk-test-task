package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    public SelenideElement
            phoneOrEmailInput = $x("//*[@id='index_email']"),
            enterButton = $x("//div[contains(@class, 'VkIdForm')]//button[contains(@class, 'signIn')]");
}
