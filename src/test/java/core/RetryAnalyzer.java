package core;

import common.Tools;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import testData.Settings;

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = Integer.parseInt(Configurator.getSetting(Settings.retryAttemptsIfTestFailed.name()));

    @Override
    public boolean retry(ITestResult result) {
        if(counter < retryLimit)
        {
            counter++;
            return true;
        }
        return false;
    }
}
