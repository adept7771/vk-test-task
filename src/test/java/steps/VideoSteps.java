package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.Tools;
import org.testng.Assert;
import pagesAndElements.VideoPage;

import static com.codeborne.selenide.Condition.visible;

public class VideoSteps {

    public void checkVideoIsExistInMyAlbum(String videoFileName){
        videoPage.myVideosLink.shouldBe(visible).click();
        SelenideElement videoTestFile = Tools.replaceTextInLocator(
                        videoPage.uploadedVideoFileAbstract,
                        "TO_REPLACE",
                        videoFileName);
        videoTestFile.shouldBe(visible);
        Assert.assertTrue(videoTestFile.isDisplayed(), "Test data video file not found in VK" +
                "library: " + videoFileName);
    }

    VideoPage videoPage = new VideoPage();
}
