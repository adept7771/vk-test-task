package testData;

import org.testng.ITestContext;
import org.testng.xml.XmlTest;
import testData.additionalClasses.MessageAttachSource;
import testData.additionalClasses.MessageAttachType;

import java.util.ArrayList;
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


    }
}
