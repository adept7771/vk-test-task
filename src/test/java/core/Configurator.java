package core;

import com.codeborne.selenide.Configuration;
import common.Tools;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.annotations.*;
import testData.Settings;
import testData.TestDataChecker;

import java.awt.*;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class Configurator implements ITestNGListener {

    private static final Logger log = LogManager.getLogger(Configurator.class);

    @BeforeSuite
    public void setUp(ITestContext iTestContext) {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true)
                .savePageSource(true));
        recognizeFrameworkSettings();
        if(getSetting(Settings.needToCheckTestData.name()).equals("true")){
            new TestDataChecker().checkAttachmentsInVkIsExist(iTestContext);
        }
    }

    @AfterMethod
    public void tearDown() {
//        attachScreenshot("Last screenshot");
//        attachPageSource();
//        attachAsText("Browser console logs", getConsoleLogs());
        closeWebDriver();
    }

    /*
    Смысл метода - дернуть из памяти настройку с именем, которое есть в файле. Если такая настройка есть,
    то будет применена настройка из памяти. Если ее нет - то будет применена настройка из файла.
     */
    public static String getSetting(String settingName){
        if(System.getProperty(settingName) == null){
            return String.valueOf(Settings.valueOf(settingName).val);
        }
        else {
            return System.getProperty(settingName);
        }
    }

    public void recognizeFrameworkSettings(){
        Configuration.browser = getSetting(Settings.browser.name());
        String setting = getSetting(Settings.timeToWaitElementMS.name());
        Configuration.timeout = Long.parseLong(setting);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String screenSizeWidth = String.valueOf(screenSize.getWidth()).replace(".0", "");
        String screenSizeHeight = String.valueOf(screenSize.getHeight()).replace(".0", "");
        if(getSetting(Settings.startMaximized.name()).equals("true")){
            Configuration.browserSize = screenSizeWidth + "x" + screenSizeHeight;
        }
    }
}
