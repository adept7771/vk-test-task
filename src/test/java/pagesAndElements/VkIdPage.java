package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class VkIdPage {

    public SelenideElement
            passwordInput = $x("//*[@name='password']"),
            continueLoginButton = $x("//div[contains(@class, 'EnterPassword')]/button[@type='submit']");

    // В общем то так широко код писать без переносов - конечно это не кошерно, но, при отладке, очень
    // часто надо подхватить какой-то элемент по локатору скопипастив его отсюда. А когда у нас
    // локатор разнесен по 2-3-4 строкам, сделать это быстро не получится. Поэтому тут предпочитаю
    // идти сознательно на нарушения codestyle, либо по договоренности с командой.
}
