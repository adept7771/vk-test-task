package tests;

import common.Generator;
import core.Configurator;
import org.testng.annotations.Parameters;
import steps.LoginSteps;
import steps.MessengerSteps;
import org.testng.annotations.Test;

public class AttachTest extends Configurator {

    @Test
    @Parameters({"attachType", "attachSource", "attachFileName"}) // немножко параметризации
    public void dummyAttachTest(String attachType, String attachSource, String attachFileName){
        loginSteps.login("79905263065", "Awwck9vVxnP6jDK");
        String chatName = Generator.latinString(30);
        messengerSteps.createNewChat(chatName);
        messengerSteps.checkChatExistInChatList(chatName); // можно все эти шаги по созданию чата так же
        messengerSteps.openChatWithName(chatName); // объединить в бОльшую цепочку действий
        messengerSteps.checkCurrentChatIsClean();
        messengerSteps.attachFileToChat(attachType, attachSource, attachFileName);
        messengerSteps.assertFileExistInChat(attachType, attachFileName);
    }

    MessengerSteps messengerSteps = new MessengerSteps();
    LoginSteps loginSteps = new LoginSteps();
}
