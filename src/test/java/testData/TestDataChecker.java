package testData;

import common.Tools;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.xml.XmlTest;
import steps.*;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataChecker {
    public void checkAttachmentsInVkIsExist(ITestContext iTestContext) {
        List<XmlTest> listOfTests = iTestContext.getSuite().getXmlSuite().getTests();
        HashMap<String, String> testDataToCheck = new HashMap<>();
        ArrayList<Map<String, String>> dataToCheck = new ArrayList<>();

        // формирование списка кейсов, и тестовых данных, которые надо проверить в библиотеке VK
        // можно вынести как отдельный метод
        listOfTests.forEach(test -> {
            Map<String, String> allParameters = test.getAllParameters();
            if (allParameters.containsKey("attachType") && allParameters.containsKey("attachSource")) {
                String attachSource = allParameters.get("attachSource");
                if (attachSource.equals(MessageAttachSource.VK.name())) {
                    dataToCheck.add(allParameters);
                }
            }
        });
        if (dataToCheck.isEmpty()) {
            return;
        } else {
            LoginSteps loginSteps = new LoginSteps();
            loginSteps.login(Users.user1Login, Users.user1pass);
        }
        CommonSteps commonSteps = new CommonSteps();

        dataToCheck.forEach(testParams -> {
            commonSteps.openSite();
            String attachType = testParams.getOrDefault("attachType", null);
            String attachSource = testParams.getOrDefault("attachSource", null);
            String attachFileName = testParams.getOrDefault("attachFileNameOrDescription", null);
            if (attachType == null || attachSource == null || attachFileName == null) {
                Assert.fail("Some of test parameters is null. Cant parse it. attachType " + attachType + " attachSource "
                        + attachSource + " attachFileName " + attachFileName);
            }
            if (attachType.equals(MessageAttachType.PICTURE.name())) {
                commonSteps.openPhotos();
                PhotosSteps photosSteps = new PhotosSteps();
                photosSteps.checkPhotoIsExistInAlbums(attachFileName);
            } else if (attachType.equals(MessageAttachType.AUDIO.name())) {
                commonSteps.openMusic();
                MusicSteps musicSteps = new MusicSteps();
                musicSteps.checkAudioIsExistInMyAlbum(attachFileName);
            } else {
                commonSteps.openVideos();
                VideoSteps videoSteps = new VideoSteps();
                videoSteps.checkVideoIsExistInMyAlbum(attachFileName);
            }
        });
    }
}
