package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import pagesAndElements.LeftMenuGlobal;

import static com.codeborne.selenide.Condition.visible;

public class CommonSteps {

    public void openSite() { // Не захотел этот метод в before аннотациях реализовывать. Но можно и там.
        Selenide.open("https://vk.com");
    }

    public void openMessenger(){
        leftMenuGlobal.messengerButton.shouldBe(visible).click();
    }

    //TODO
    public void checkTestDataIsExists(){

    }

    LeftMenuGlobal leftMenuGlobal = new LeftMenuGlobal();
}
