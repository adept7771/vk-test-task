package common;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$x;

public class Tools {

    private static Logger log = LogManager.getLogger(Tools.class);

    public static String getSystemMonthInMMMFormat() {
        return new SimpleDateFormat("MMM", Locale.ENGLISH)
                .format(Calendar.getInstance().getTime());
    }

    public static String getSystemDateIn_dd_MMM_yyyy() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        return formatter.format(date);
    }

    public static String removeAllSpacesFromString(String incomingString) {
        String normalizedString = (incomingString.replaceAll("[\\-\\+\\.\\^:,]", ""))
                .replaceAll(" ", "");
        return normalizedString;
    }

    public static String removeAllLettersFromString(String incomingString) {
        String normalizedString = (incomingString.replaceAll("[^0-9.]", ""));
        return normalizedString;
    }

    public static String removeAllLettersAndSpacesFromString(String incomingString) {
        String normalizedString = removeAllSpacesFromString(incomingString);
        return removeAllLettersFromString(normalizedString);
    }

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        int result = r.nextInt((max - min) + 1) + min;
        //System.out.println("rand int: " + result);
        return result;
    }

    public static String getFirstWordFromString(String string) {
        if (string.contains(" ")) {
            string = string.substring(0, string.indexOf(" "));
            return string;
        } else {
            log.warn("Can't get first word from a string");
            return null;
        }
    }

    public static String returnStringWithoutDoubleSpaces(String incomingString) {
        if (incomingString.contains("  ")) {
            return incomingString.replaceAll(" {2}", " ");
        } else {
            return incomingString;
        }
    }

    public static void moveWindowToDimension(int xPoint, int yPoint){
        WebDriverRunner.driver().getWebDriver().manage().window().setPosition(
                new Point(xPoint, yPoint));
    }

    public static void setWindowSize(int xDimension, int yDimension) {
        WebDriverRunner.driver().getWebDriver().manage().window()
                .setSize(new Dimension(xDimension, yDimension));
    }

    public static void moveWindowAndSetSize(int xPointToMove, int yPointToMove,
                                            int xWindowSize, int yWindowSize) {
        moveWindowToDimension(xPointToMove, yPointToMove);
        setWindowSize(xWindowSize, yWindowSize);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static SelenideElement replaceTextInLocator
            (SelenideElement element, String textToReplace, String replacement) {
        return $x(element.getSearchCriteria()
                .replace(textToReplace, replacement)
                .replace("By.xpath: ", "")
                .replace("By.css: ", ""));
    }
}
