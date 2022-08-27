package steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import common.Tools;
import org.testng.Assert;
import pagesAndElements.MessengerPage;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static com.codeborne.selenide.Condition.visible;

public class MessengerSteps {

    public void createNewChat(String chatName) {
        commonSteps.openMessenger();
        messengerPage.chatNameInListAbstract.shouldBe(visible);
        messengerPage.createNewChatButton.shouldBe(visible).click();
        try {
            messengerPage.newChatNameInput.shouldBe(
                    visible, Duration.ofMillis(1100)).val(chatName);
        } catch (ElementShould exception) {
            // здесь почему-то не всегда отрабатывает клик пришлось вставить кастомное короткое ожидание
            // максимально на 1100мс (стандартное обычно выше), если не сработало, то пытаемся снова
            messengerPage.createNewChatButton.shouldBe(visible).click();
            messengerPage.newChatNameInput.shouldBe(visible).val(chatName);
        }
        messengerPage.chatCreateButtonConfirm.shouldBe(Condition.interactable).click();
        messengerPage.chatNameInListAbstract.shouldBe(visible);
    }

    public void checkChatExistInChatList(String chatName) {
        messengerPage.chatNameInListAbstract.shouldBe(visible);
        messengerPage.chatNameInListCollection.shouldBe(
                CollectionCondition.sizeGreaterThanOrEqual(1));
        messengerPage.chatNameInListCollection.shouldHave(
                CollectionCondition.itemWithText(chatName));
    }

    public void checkCurrentChatIsClean() {
        messengerPage.chatMessageTimeLinkAbstract.shouldBe(
                Condition.not(visible), Duration.ofSeconds(500));
    }

    public void openChatWithName(String chatName) {
        messengerPage.chatNameInListAbstract.shouldBe(visible);
        messengerPage.chatNameInListCollection.shouldBe(
                CollectionCondition.sizeGreaterThanOrEqual(1));
        messengerPage.chatNameInListCollection.findBy(
                Condition.exactText(chatName)).click();
        messengerPage.currentActiveChatName
                .shouldBe(visible)
                .shouldBe(Condition.exactText(chatName));
    }

    public void assertFileExistInChat(String attachType, String fileName) {
        // по-хорошему и для полноты проверки надо бы выкачать еще аудио и видео файл, и сравнить
        // размеры с размерами тех файлов, что лежат в библиотеке VK
        if (attachType.equals("audio")) {
            SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                            messengerPage.attachedAudioFileInChatAbstract,
                            "TO_REPLACE", fileName)
                    .shouldBe(visible);
            Assert.assertTrue(audioFileInChatElement.isDisplayed());
            return;
        }
        if (attachType.equals("video")) {
            SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                            messengerPage.attachedVideoFileInChatAbstract,
                            "TO_REPLACE", fileName)
                    .shouldBe(visible);
            Assert.assertTrue(audioFileInChatElement.isDisplayed());
            return;
        }
    }

    public void attachFileToChat(String attachType, String attachSource, String fileName) {
        if (attachType.equals("picture")) { // типы аттачей можно вынести в отдельный Enum или вспомогательный класс
            if (attachSource.equals("local")) {

            } else {

            }
            return;
        }
        if (attachType.equals("audio")) {
            if (attachSource.equals("local")) {

            } else {
                attachAudioToChatFromVKCollection(fileName);
            }
            return;
        }
        if (attachType.equals("video")) {
            if (attachSource.equals("local")) {

            } else {
                attachVideoToChatFromVKCollection(fileName);
            }
        }
    }

    public void attachLocalAudioToChat(String fileLocalPath) {

    }

    public void attachAudioToChatFromVKCollection(String nameInCollection) {
        messengerPage.attachFileFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.attachAudioFromVKCollectionButton.shouldBe(visible).click();
        SelenideElement attachFileWithNameButton =
                Tools.replaceTextInLocator(messengerPage.audioFileInVkCollectionAbstract,
                        "TO_REPLACE", nameInCollection);
        attachFileWithNameButton.shouldBe(visible).click();
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
    }

    public void attachLocalVideoToChat(String fileLocalPath) {

    }

    public void attachVideoToChatFromVKCollection(String nameInCollection) {
        messengerPage.attachFileFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.attachVideoFromVKCollectionButton.shouldBe(visible).click();
        SelenideElement attachFileWithNameButton =
                Tools.replaceTextInLocator(messengerPage.videoFileInVkCollectionAbstract,
                        "TO_REPLACE", nameInCollection);
        attachFileWithNameButton.shouldBe(visible).click();
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
    }

    public void attachLocalPictureToChat(String fileLocalPath) {

    }

    public void attachPictureToChatFromVKCollection(String fileLocalPath) {

    }

    MessengerPage messengerPage = new MessengerPage();
    CommonSteps commonSteps = new CommonSteps();
}
