package pagesAndElements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MessengerPage {

    public SelenideElement
            // Привязываться к тексту в локаторах, если будем тестить мультиязычность - плохая идея,
            // но иначе будут длиннющие страшнющие локаторы. Пока так сделал. Но обсуждаемо.
            // Abstract тут - любой, не конкретно указанный элемент, как-правило это кастомизируемый элемент,
            // либо таких элементов много и этот элемент будет просто первым.
            // TO_REPLACE как правило абстрактный элемент с привязкой по какому-либо атрибуту, который мы
            // скармливаем статично-фабричному методу на переделку нужного нам локатора on-demand
            createNewChatButton = $x("//button[contains(@aria-label, 'Новый чат')]"),
            newChatNameInput = $x("//input[@id='im_dialogs_creation_name']"),
            chatNameInListAbstract = $x("//span[@class='_im_dialog_link']"),
            chatCreateButtonConfirm = $x("//button[contains(@class, 'im_confirm_creation')]"),
            currentActiveChatName = $x("//a[contains(@class, '_im_page_peer_name')]"),
            attachFileFromVKCollectionButton = $x("//a[contains(@class, 'ms_item_more')]"),
            attachPictureFromVKCollectionButton = $x("//a[contains(@class, 'type_photo')]"),
            pictureFileFirstInVKCollection = $x("//a[contains(@id, 'photos_choose_row')]"),
            attachedPictureFileInChatAbstract = $x("//a[contains(@href, 'photo') and contains(@aria-label, 'TO_REPLACE')]"),
            attachAudioFromVKCollectionButton = $x("//a[contains(@class, 'type_audio')]"),
            attachedAudioFileInChatAbstract = $x("//*[contains(@class, 'audio_row__inner')]//a[contains(@href, 'TO_REPLACE')]"),
            audioFileInVkCollectionAbstract = $x("//a[contains(@class, 'artist_link') and contains(text(),'TO_REPLACE')]/ancestor::div//div[@class='ape_attach']"),
            attachVideoFromVKCollectionButton = $x("//a[contains(@class, 'type_video')]"),
            attachedVideoFileInChatAbstract = $x("//div[contains(@class, 'a post_video_title') and contains(text(), 'TO_REPLACE')]"),
            videoFileInVkCollectionAbstract = $x("(//a[@class='video_item_title' and contains(text(), 'TO_REPLACE')]/ancestor::div[contains(@id, 'video_item')]/a)[1]"),
            sendMessageInChatButton = $x("//*[contains(@class, 'im-chat-input')]//*[contains(@class, 'im-send-btn__icon--send')]"),
            chatMessageTimeLinkAbstract = $x("//a[@class='_im_mess_link']"),
            chatMessagePictureAbstract = $x("//div[contains(@class, '_im_msg_media')]"),
            chatMessagePictureFullScreen = $x("//div[@id='pv_photo']/img"),
            chatMessageVideoSendPreview = $x("//div[@class='VideoPreview__thumb']"),
            chatMessageVideoWrapper = $x("//div[@class='inline_video_wrap']"),
            chatMessageAttachPhotoOrVideoInputHidden = $x("//input[@class='im-chat-input--attach-file']");

    public ElementsCollection
            chatNameInListCollection = $$x("//span[@class='_im_dialog_link']"),
            audioFileInVkCollection = $$x("//a[contains(@class, 'artist_link') and contains(text(),'TO_REPLACE')]/ancestor::div//div[@class='ape_attach']");
}
