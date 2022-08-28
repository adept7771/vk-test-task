package steps;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.annotations.BeforeTest;
import pagesAndElements.LeftMenuGlobal;
import pagesAndElements.LoginPage;
import testData.Settings;

import static com.codeborne.selenide.Condition.visible;

public class CommonSteps {

    public void openSite() { // Не захотел этот метод в before аннотациях реализовывать. Но можно и там.
        Selenide.open(Settings.testAddress);
    }

    public void openMessenger() {
        leftMenuGlobal.messengerButton.shouldBe(visible).click();
    }

    public void openPhotos() {
        leftMenuGlobal.photosButton.shouldBe(visible).click();
    }

    public void openMusic() {
        leftMenuGlobal.musicButton.shouldBe(visible).click();
    }

    public void openVideos() {
        leftMenuGlobal.videosButton.shouldBe(visible).click();
    }

    LeftMenuGlobal leftMenuGlobal = new LeftMenuGlobal();
}
