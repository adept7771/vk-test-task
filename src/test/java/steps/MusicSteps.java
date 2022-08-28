package steps;

import com.codeborne.selenide.SelenideElement;
import common.Tools;
import org.testng.Assert;
import pagesAndElements.MusicPage;

import static com.codeborne.selenide.Condition.visible;

public class MusicSteps {

    public void checkAudioIsExistInMyAlbum(String musicFileName){
        musicPage.myMusic.shouldBe(visible).click();
        SelenideElement testDataAudioFile = Tools.replaceTextInLocator(
                musicPage.audioInAlbumAbstractCustomText,
                "TO_REPLACE",
                musicFileName);
        testDataAudioFile.shouldBe(visible);
        Assert.assertTrue(testDataAudioFile.isDisplayed(), "Test audio file in VK collection not" +
                "found: " + musicFileName);
    }

    MusicPage musicPage = new MusicPage();
}
