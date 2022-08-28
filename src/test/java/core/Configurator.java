package core;

import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import testData.TestDataChecker;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class Configurator implements ITestNGListener {

    private static final Logger log = LogManager.getLogger(Configurator.class);

    @BeforeSuite
    public void setUp(ITestContext iTestContext) {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true)
                .savePageSource(true));
        new TestDataChecker().checkAttachmentsInVkIsExist(iTestContext);
        System.out.println();
//        recognizeFrameworkSettings();
    }

//    @BeforeSuite
//    private void checkTestDataExist(ITestContext iTestContext){
//        System.out.println(iTestContext);
//        DataChecker dataChecker = new DataChecker();
//        dataChecker.checkAttachmentsInVkIsExist(iTestContext);
//        System.out.println("123");
//    }

    @AfterClass
    public void tearDown() {
//        attachScreenshot("Last screenshot");
//        attachPageSource();
//        attachAsText("Browser console logs", getConsoleLogs());
        closeWebDriver();
    }

    private void checkAttachmentsInVkIsExist(ITestContext iTestContext){

    }

    /**
     * Метод, загружающий настройки в селенид, исходя из заданных в системе и enum Props
     */
//    public void recognizeFrameworkSettings(){
//        if (Properties.showStartupLogs.val.equals("true")) {
//            try (InputStream input = new FileInputStream("src/test/resources/junit-platform.properties")) {
//                java.util.Properties prop = new java.util.Properties();
//                prop.load(input);
//                log.info("Заданное количество параллельных потоков (файл junit-platform.properties): " +
//                        prop.getProperty("junit.jupiter.execution.parallel.config.fixed.parallelism"));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        Configuration.browser = Tools.getRunOptionFromSystemOrProps(Properties.browser.name());
//        Configuration.timeout = Integer.parseInt(Tools.getRunOptionFromSystemOrProps
//                (Properties.elementsWaitTimeout.name()));
//        Configuration.startMaximized = Boolean.parseBoolean(Tools.getRunOptionFromSystemOrProps
//                (Properties.startMaximized.name()));
//        if (Tools.getRunOptionFromSystemOrProps
//                (Properties.testInstanceDestination.name()).equals("selenoid")) {
//            String finalSelenoidAddress = Properties.selenoidAddress.val + ":" + Properties.selenoidPort.val + "/wd/hub/";
//            log.info("Тесты будут запущены в инстансе селеноида: " + finalSelenoidAddress);
//
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities = Tools.getCapabilityFromSystemOrProps(capabilities,
//                    Properties.enableVideo.name());
//            capabilities = Tools.getCapabilityFromSystemOrProps(capabilities,
//                    Properties.enableVNC.name());
//
//            Configuration.browserCapabilities = capabilities;
//            Configuration.remote = finalSelenoidAddress;
//        }
//    }
}
