package steps;

import com.codeborne.selenide.SelenideElement;
import common.Tools;
import org.testng.Assert;
import pagesAndElements.PhotosPage;

import static com.codeborne.selenide.Condition.visible;

public class PhotosSteps {

    public void checkPhotoIsExistInAlbums(String photoName) {
        SelenideElement testPhoto = Tools.replaceTextInLocator(photosPage.pictureInAlbumAbstractCustomLabel,
                "TO_REPLACE", photoName).shouldBe(visible);

        Assert.assertTrue(testPhoto.isDisplayed(), "Test photo for uploading in chat from" +
                "VK collection not found by name " + photoName);
    }

    PhotosPage photosPage = new PhotosPage();
}
