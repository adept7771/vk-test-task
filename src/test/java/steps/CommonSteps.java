package steps;

import com.codeborne.selenide.Selenide;
import common.Tools;
import core.Configurator;
import io.qameta.allure.Step;
import pagesAndElements.LeftMenuGlobal;
import common.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.visible;

public class CommonSteps {

    @Step("Шаг - открытие сайта")
    public void openSite() { // Не захотел этот метод в before аннотациях реализовывать. Но можно и там.
        Selenide.open(Configurator.getSetting(Settings.testAddress.name()));
        Tools.moveWindowToDimension(0, 0);
    }

    public void openMessenger() {
        leftMenuGlobal.messengerButton.shouldBe(visible).click();
    }

    public void openPhotos() {
        leftMenuGlobal.photosButton.shouldBe(visible).click();
    }

    public void openMusic() {
        leftMenuGlobal.musicButton.shouldBe(visible).click();
    }

    public void openVideos() {
        leftMenuGlobal.videosButton.shouldBe(visible).click();
    }

    public boolean compareTwoFilesUsingFileSize(String fileWithPath1, String fileWithPath2, double allowedDiffPercentage){
        try {
            double sizeFile1 = Files.size(Paths.get(fileWithPath1));
            double sizeFile2 = Files.size(Paths.get(fileWithPath2));
            if(allowedDiffPercentage == 0){
                return sizeFile1 == sizeFile2;
            }
            else {
                if(sizeFile1 == sizeFile2){
                    return true;
                }
                else {
                    double difference = sizeFile1 - sizeFile2;
                    if(difference < 0){
                        difference *= -1;
                    }
                    double biggerValue = Math.max(sizeFile1, sizeFile2);
                    double currentDiffPercentage = (difference * 100) / biggerValue;

                    if(currentDiffPercentage > allowedDiffPercentage){
                        return false;
                    }
                    else {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean assertTwoFilesIsEqualsLineByLine(String fileWithPath1, String fileWithPath2) {
        BufferedReader reader1, reader2 = null;
        try {
            reader1 = new BufferedReader(new FileReader(fileWithPath1));
            reader2 = new BufferedReader(new FileReader(fileWithPath2));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line1, line2 = null;
        try {
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean areEqual = true;
        int lineNum = 1;
        while (line1 != null || line2 != null) {
            if (line1 == null || line2 == null) {
                areEqual = false;
                break;
            } else if (!line1.equalsIgnoreCase(line2)) {
                areEqual = false;
                break;
            }
            try {
                line1 = reader1.readLine();
                line2 = reader2.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lineNum++;
        }
        try {
            reader1.close();
            reader2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (areEqual) {
            return true;
        } else {
            System.out.println("Two files have different content. They differ at line " + lineNum);
            System.out.println("File1 has " + line1 + " and File2 has " + line2 + " at line " + lineNum);
            return false;
        }
    }
    LeftMenuGlobal leftMenuGlobal = new LeftMenuGlobal();
}
