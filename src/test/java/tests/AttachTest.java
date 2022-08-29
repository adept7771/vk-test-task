package tests;

import common.Generator;
import core.Configurator;
import core.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import steps.CommonSteps;
import steps.LoginSteps;
import steps.MessengerSteps;
import org.testng.annotations.Test;
import testData.Users;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

public class AttachTest extends Configurator {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Parameters({"attachType", "attachSource", "attachFileNameOrPath"}) // немножко параметризации
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Прикрепление файлов")
    @Story("Мессенджер")
    @Owner("Dmitry Potapov")
    public void attachFilesToChatTest(MessageAttachType attachType, MessageAttachSource attachSource,
                                String attachFileNameOrPath
    ){
        loginSteps.login(Users.user1Login, Users.user1pass);
        String chatName = Generator.latinString(30);
        messengerSteps.createNewChat(chatName);
        messengerSteps.checkChatExistInChatList(chatName); // можно все эти шаги по созданию чата так же
        messengerSteps.openChatWithName(chatName); // объединить в бОльшую цепочку действий
        messengerSteps.checkCurrentChatIsClean();
        messengerSteps.attachFileToChat(attachType, attachSource, attachFileNameOrPath);
        messengerSteps.assertFileExistInChat(attachType, attachFileNameOrPath);
    }

    MessengerSteps messengerSteps = new MessengerSteps();
    LoginSteps loginSteps = new LoginSteps();
}
