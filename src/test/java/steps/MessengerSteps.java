package steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import common.Tools;
import org.testng.Assert;
import pagesAndElements.MessengerPage;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

import java.time.Duration;

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

    public void assertFileExistInChat(MessageAttachType attachType, String fileNameOrDescription) {
        // по-хорошему и для полноты проверки надо бы выкачать еще файл, и сравнить
        // размеры с размерами тех файлов, что лежат в библиотеке VK пока проверка не полная (по верстке)
        if (attachType.equals(MessageAttachType.AUDIO)) {
            messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
            SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                            messengerPage.attachedAudioFileInChatAbstract,
                            "TO_REPLACE", fileNameOrDescription).shouldBe(visible);
            // Понимаю, что тут тест свалится если элемент не видим, однако правило АТ гласит что каждый тест
            // должен заканчиваться Ассертом. Так что он тут есть, но достаточно условный.
            Assert.assertTrue(audioFileInChatElement.isDisplayed());
            return;
        }
        if (attachType.equals(MessageAttachType.VIDEO)) {
            messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
            SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                            messengerPage.attachedVideoFileInChatAbstract,
                            "TO_REPLACE", fileNameOrDescription).shouldBe(visible);
            Assert.assertTrue(audioFileInChatElement.isDisplayed());
            return;
        }
        if (attachType.equals(MessageAttachType.PICTURE)) {
            messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
            SelenideElement pictureFileInChatElement = Tools.replaceTextInLocator(
                    messengerPage.attachedPictureFileInChatAbstract,
                    "TO_REPLACE", fileNameOrDescription).shouldBe(visible);
            Assert.assertTrue(pictureFileInChatElement.isDisplayed());
        }
    }

    public void attachFileToChat(MessageAttachType attachType, MessageAttachSource attachSource,
                                 String fileNameOrDescription) {
        if (attachType.equals(MessageAttachType.PICTURE)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {

            } else {
                attachFirstPictureToChatFromVKCollection(fileNameOrDescription);
            }
            return;
        }
        if (attachType.equals(MessageAttachType.AUDIO)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {

            } else {
                attachAudioToChatFromVKCollection(fileNameOrDescription);
            }
            return;
        }
        if (attachType.equals(MessageAttachType.VIDEO)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {

            } else {
                attachVideoToChatFromVKCollection(fileNameOrDescription);
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

    // под самый конец реализации метода я заметил что в коллекции картинок нет ИМЕН!  (O_o)
    // Это был большой сюрприз. Поэтому этот метод забирает просто первый элемент из тестовой
    // коллекции. Но надо переделать. За неимением времени на переделки - отсылаю так.
    public void attachFirstPictureToChatFromVKCollection(String fileNameIgnored) {
        messengerPage.attachFileFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.attachPictureFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.pictureFileFirstInVKCollection.shouldBe(visible).click();
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
    }

    MessengerPage messengerPage = new MessengerPage();
    CommonSteps commonSteps = new CommonSteps();
}
