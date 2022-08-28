package testData;

import org.testng.ITestContext;
import org.testng.xml.XmlTest;
import steps.*;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataChecker {
    public void checkAttachmentsInVkIsExist(ITestContext iTestContext) {
        List<XmlTest> listOfTests = iTestContext.getSuite().getXmlSuite().getTests();
        HashMap<String, String> testDataToCheck = new HashMap<>();

        // формирование списка кейсов, и тестовых данных, которые надо проверить в библиотеке VK
        // можно вынести как отдельный метод
        listOfTests.forEach(test -> {
            Map<String, String> allParameters = test.getAllParameters();
            if (allParameters.containsKey("attachType") && allParameters.containsKey("attachSource")) {
                String attachType = allParameters.get("attachType");
                String attachSource = allParameters.get("attachSource");
                if (attachSource.equals(MessageAttachSource.VK.name())) {
                    if (!testDataToCheck.containsKey(attachType)) {
                        testDataToCheck.put(attachType, attachSource);
                    }
                }
            }
        });

        if(testDataToCheck.isEmpty()){
            return;
        }
        else {
            LoginSteps loginSteps = new LoginSteps();
            loginSteps.login(Users.user1Login, Users.user1pass);
        }
        CommonSteps commonSteps = new CommonSteps();

        testDataToCheck.forEach((attachType, attachSource) -> {
            commonSteps.openSite();
            if(attachType.equals(MessageAttachType.PICTURE.name())){
                commonSteps.openPhotos();
                PhotosSteps photosSteps = new PhotosSteps();
                photosSteps.checkPhotoIsExistInAlbums("test");
            }
            else if(attachType.equals(MessageAttachType.AUDIO.name())){
                commonSteps.openMusic();
                MusicSteps musicSteps = new MusicSteps();
                musicSteps.checkAudioIsExistInMyAlbum("test");
            }
            else{
                commonSteps.openVideos();
                VideoSteps videoSteps = new VideoSteps();
                videoSteps.checkVideoIsExistInMyAlbum("test");
            }
        });
    }
}
