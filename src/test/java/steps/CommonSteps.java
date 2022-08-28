package steps;

import com.codeborne.selenide.Selenide;
import common.Tools;
import core.Configurator;
import pagesAndElements.LeftMenuGlobal;
import testData.Settings;

import static com.codeborne.selenide.Condition.visible;

public class CommonSteps {

    public void openSite() { // Не захотел этот метод в before аннотациях реализовывать. Но можно и там.
        Selenide.open(Configurator.getSetting(Settings.testAddress.name()));
        Tools.moveWindowToDimension(0, 0);
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
