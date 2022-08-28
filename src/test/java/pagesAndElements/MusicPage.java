package pagesAndElements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MusicPage {

    public SelenideElement
            myMusic = $x("//*[contains(@class, 'my _audio_section_tab__all')]/a"),
            audioInAlbumAbstractCustomText = $x("//*[contains(@class, 'audio_row__title')]/a[contains(text(), 'TO_REPLACE')]");
}
