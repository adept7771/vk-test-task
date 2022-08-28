package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LeftMenuGlobal {
    public SelenideElement
            messengerButton = $x("//div[@class='side_bar_inner']//*[@href='/im']"),
            musicButton = $x("//nav[contains(@class, 'side_bar_nav')]//a[contains(@href, 'audios')]"),
            photosButton = $x("//nav[contains(@class, 'side_bar_nav')]//a[contains(@href, 'albums')]"),
            videosButton = $x("//nav[contains(@class, 'side_bar_nav')]//a[contains(@href, 'video')]"),
            blogLink = $x("//*[@href='/blog']");
}
