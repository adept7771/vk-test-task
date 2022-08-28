package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class PhotosPage {

    public SelenideElement
            pictureInAlbumAbstractCustomLabel = $x("//*[contains(@aria-label, 'TO_REPLACE')]");
}
