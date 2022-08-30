package steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import common.Settings;
import common.Tools;
import io.qameta.allure.Step;
import org.testng.Assert;
import pagesAndElements.MessengerPage;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;

public class MessengerSteps {

    @Step("Шаг - создание нового чата с именем {0}")
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

    @Step("Шаг - чат существует в списке чатов с именем {0}")
    public void checkChatExistInChatList(String chatName) {
        messengerPage.chatNameInListAbstract.shouldBe(visible);
        messengerPage.chatNameInListCollection.shouldBe(
                CollectionCondition.sizeGreaterThanOrEqual(1));
        messengerPage.chatNameInListCollection.shouldHave(
                CollectionCondition.itemWithText(chatName));
    }

    @Step("Шаг - текущий чат пуст")
    public void checkCurrentChatIsClean() {
        messengerPage.chatMessageTimeLinkAbstract.shouldBe(
                Condition.not(visible), Duration.ofSeconds(500));
    }

    @Step("Шаг - открыть чат по имени {0}")
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

    @Step("Шаг - подтвердить что файл типа {0} и именем {1} из источника {2} есть в текущем чате")
    public void assertFileExistInChat(MessageAttachType attachType, String fileNameOrPath,
                                      MessageAttachSource attachSource) {
        // по-хорошему и для полноты проверки надо бы выкачать еще файл, и сравнить
        // размеры с размерами тех файлов, что лежат в библиотеке VK пока проверка не полная (по верстке)
        if (attachType.equals(MessageAttachType.AUDIO)) {
            messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
            SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                    messengerPage.attachedAudioFileInChatAbstract,
                    "TO_REPLACE", fileNameOrPath).shouldBe(visible);
            // Понимаю, что тут тест свалится если элемент не видим, однако правило АТ гласит что каждый тест
            // должен заканчиваться Ассертом. Так что он тут есть, но достаточно условный.
            Assert.assertTrue(audioFileInChatElement.isDisplayed());
            return;
        }
        if (attachType.equals(MessageAttachType.VIDEO)) {
            if(attachSource.equals(MessageAttachSource.LOCAL)){
                messengerPage.chatMessageVideoWrapper.shouldBe(visible);
                // Проверка так же не полная. Просто смотрим что появился видео враппер. Надо думать так же
                // как сравнивать два видео файла.
                Assert.assertTrue(messengerPage.chatMessageVideoWrapper.isDisplayed());
            }
            else {
                messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
                SelenideElement audioFileInChatElement = Tools.replaceTextInLocator(
                        messengerPage.attachedVideoFileInChatAbstract,
                        "TO_REPLACE", fileNameOrPath).shouldBe(visible);
                Assert.assertTrue(audioFileInChatElement.isDisplayed());
            }

            return;
        }
        if (attachType.equals(MessageAttachType.PICTURE)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {
                messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
                downloadLastPictureInChatAs("test");

                Assert.assertTrue(commonSteps.compareTwoFilesUsingFileSize(
                                Settings.pathWithDownloadedTmpFiles.val + "test.jpeg",
                                Settings.pathWithOriginalFilesToUpload.val + "test.jpeg",
                                Double.parseDouble(Settings.diffSizePercentageToCompareImages.val)),
                        "Pictures is bigger by size then defined system trash hold");

                Assert.assertTrue(commonSteps.compareTwoPicturesViaDimensions(
                        Settings.pathWithDownloadedTmpFiles.val + "test.jpeg",
                        Settings.pathWithOriginalFilesToUpload.val + "test.jpeg"));
            } else {
                messengerPage.chatMessageTimeLinkAbstract.shouldBe(visible);
                SelenideElement pictureFileInChatElement = Tools.replaceTextInLocator(
                        messengerPage.attachedPictureFileInChatAbstract, "TO_REPLACE",
                        fileNameOrPath).shouldBe(visible);
                Assert.assertTrue(pictureFileInChatElement.isDisplayed());
            }
        }
    }

    @Step("Шаг - прикрепить файл типа {0} из {1} и именем {2} к текущему чату")
    public void attachFileToChat(MessageAttachType attachType, MessageAttachSource attachSource,
                                 String fileNameOrPath) {
        if (attachType.equals(MessageAttachType.PICTURE)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {
                attachLocalPictureToChat(fileNameOrPath);
            } else {
                attachPictureToChatFromVKCollection(fileNameOrPath);
            }
            return;
        }
        if (attachType.equals(MessageAttachType.AUDIO)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {

            } else {
                attachAudioToChatFromVKCollection(fileNameOrPath);
            }
            return;
        }
        if (attachType.equals(MessageAttachType.VIDEO)) {
            if (attachSource.equals(MessageAttachSource.LOCAL)) {
                attachLocalVideoToChat(fileNameOrPath);
            } else {
                attachVideoToChatFromVKCollection(fileNameOrPath);
            }
        }
    }

    public void downloadLastPictureInChatAs(String downloadPictureName) {
        messengerPage.chatMessagePictureAbstract.shouldBe(visible).click();
        try {
            messengerPage.chatMessagePictureFullScreen.shouldBe(
                    visible, Duration.ofMillis(1100)).click();
        } catch (ElementShould elementShould) { // иногда не прожимается, приходится дублировать
            messengerPage.chatMessagePictureFullScreen.shouldBe(visible).click();
        }
        String pictureAddress = messengerPage.chatMessagePictureFullScreen.getAttribute("src");
        BufferedImage bufferedImage = null;
        try {
            assert pictureAddress != null;
            bufferedImage = ImageIO.read(new URL(pictureAddress));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileNameWithPath = Settings.pathWithDownloadedTmpFiles.val + downloadPictureName + ".jpeg";
        File outputFile = new File(fileNameWithPath);
        try {
            ImageIO.write(bufferedImage, "jpeg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        messengerPage.chatMessageAttachPhotoOrVideoInputHidden.shouldBe(exist)
                .uploadFile(new File(fileLocalPath));
        messengerPage.chatMessageVideoSendPreview.shouldBe(
                visible,Duration.ofSeconds(10));
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
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
        messengerPage.chatMessageAttachPhotoOrVideoInputHidden.shouldBe(exist)
                .uploadFile(new File(fileLocalPath));
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
    }

    public void attachPictureToChatFromVKCollection(String fileNameIgnored) {
        messengerPage.attachFileFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.attachPictureFromVKCollectionButton.shouldBe(visible).click();
        messengerPage.pictureFileFirstInVKCollection.shouldBe(visible).click();
        messengerPage.sendMessageInChatButton.shouldBe(visible).click();
    }

    MessengerPage messengerPage = new MessengerPage();
    CommonSteps commonSteps = new CommonSteps();
}
