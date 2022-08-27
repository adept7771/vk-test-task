package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LeftMenuGlobal {
    public SelenideElement
            messengerButton = $x("//div[@class='side_bar_inner']//*[@href='/im']"),
            blogLink = $x("//*[@href='/blog']");
}
