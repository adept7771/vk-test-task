package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class VideoPage {

    public SelenideElement
            myVideosLink = $x("(//div[@class='MenuList ']//*[contains(@href, 'video/@id')])[1]"),
            uploadedVideoFileAbstract = $x("//a[@class='VideoCard__title' and contains(text(), 'TO_REPLACE')]");
}
