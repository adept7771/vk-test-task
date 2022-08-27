package common;

import org.testng.Assert;

import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    public static String specialSymbolsString = "±!@#$%^&*()_+./,|\\[]{}\"'`~№:;?";

    public static String phoneNumber(String countryCode, String prefix, int numberLength) {
        StringBuilder finalPhoneNumber = new StringBuilder(countryCode + prefix);

        for (int i = 0; i < numberLength; i++) {
            String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1));
            finalPhoneNumber.append(randomNum);
        }
        // System.out.println("Generated phone:" + finalPhoneNumber.toString());
        return finalPhoneNumber.toString();
    }

    public static String phoneNumber(String prefix, int numberLength) {
        return phoneNumber("", prefix, numberLength);
    }

    public static String string
            (int length, boolean containsCyrillic, boolean containsLatin, boolean containsNumeric, boolean containsSpaces,
             boolean containsSpecialSymbols, boolean mixedCase, boolean upperCase) {

        String cyrillicSymbols = "", latinSymbols = "", numericString = "", symbolsString = "";

        int numberOfGenerateOptions = 0;
        numberOfGenerateOptions = containsCyrillic ? numberOfGenerateOptions + 1 : numberOfGenerateOptions;
        numberOfGenerateOptions = containsLatin ? numberOfGenerateOptions + 1 : numberOfGenerateOptions;
        numberOfGenerateOptions = containsNumeric ? numberOfGenerateOptions + 1 : numberOfGenerateOptions;
        numberOfGenerateOptions = containsSpaces ? numberOfGenerateOptions + 1 : numberOfGenerateOptions;
        numberOfGenerateOptions = containsSpecialSymbols ? numberOfGenerateOptions + 1 : numberOfGenerateOptions;

        Assert.assertTrue(numberOfGenerateOptions < length,
                "Number of options in random string generator: " + numberOfGenerateOptions +
                        " can't guarantee all matches of options for string length: " + length);
        Assert.assertNotEquals(0, numberOfGenerateOptions,
                "No one generate option was specified for generator");
        Assert.assertFalse( length < 2, "String generator, you specified mixed case, " +
                "but length of string <2 symbols!");

        cyrillicSymbols = containsCyrillic ? "абвгдежзийклмнопрстуфхцчшщэюяьйъ" : cyrillicSymbols;
        latinSymbols = containsLatin ? "abcdefghijklmnopqrstuvwxyz" : latinSymbols;
        numericString = containsNumeric ? "0123456789" : numericString;
        symbolsString = containsSpecialSymbols ? specialSymbolsString : symbolsString;
        if (upperCase) {
            cyrillicSymbols = cyrillicSymbols.equals("") ?
                    cyrillicSymbols : cyrillicSymbols.toUpperCase(Locale.ROOT);
            latinSymbols = latinSymbols.equals("") ?
                    latinSymbols : latinSymbols.toUpperCase(Locale.ROOT);
        }

        StringBuilder stringBuilder = new StringBuilder();
        int currentGenerateOptionIndex = 0;
        HashSet<String> activatedOptions = new HashSet<>();
        boolean currentCaseIsUpper = true;
        while (length-- != 0) {
            currentGenerateOptionIndex += 1;
            if (containsCyrillic && !activatedOptions.contains("containsCyrillic")) {
                activatedOptions.add("containsCyrillic");

                int charIndex = (int) (Math.random() * cyrillicSymbols.length());
                char resultChar = cyrillicSymbols.charAt(charIndex);
                if (mixedCase) {
                    if (currentCaseIsUpper) {
                        currentCaseIsUpper = false;
                        resultChar = Character.toUpperCase(resultChar);
                    } else {
                        currentCaseIsUpper = true;
                        resultChar = Character.toLowerCase(resultChar);
                    }
                }
                stringBuilder.append(resultChar);

            } else if (containsLatin && !activatedOptions.contains("containsLatin")) {
                activatedOptions.add("containsLatin");

                int charIndex = (int) (Math.random() * latinSymbols.length());
                char resultChar = latinSymbols.charAt(charIndex);
                if (mixedCase) {
                    if (currentCaseIsUpper) {
                        currentCaseIsUpper = false;
                        resultChar = Character.toUpperCase(resultChar);
                    } else {
                        currentCaseIsUpper = true;
                        resultChar = Character.toLowerCase(resultChar);
                    }
                }
                stringBuilder.append(resultChar);

            } else if (containsNumeric && !activatedOptions.contains("containsNumeric")) {
                activatedOptions.add("containsNumeric");
                int charIndex = (int) (Math.random() * numericString.length());
                stringBuilder.append(numericString.charAt(charIndex));

            } else if (containsSpaces && !activatedOptions.contains("containsSpaces")) {
                activatedOptions.add("containsSpaces");
                stringBuilder.append(" ");

            } else if (containsSpecialSymbols && !activatedOptions.contains("containsSpecialSymbols")) {
                activatedOptions.add("containsSpecialSymbols");
                int charIndex = (int) (Math.random() * specialSymbolsString.length());
                stringBuilder.append(specialSymbolsString.charAt(charIndex));
            }
            if (currentGenerateOptionIndex == numberOfGenerateOptions) {
                currentGenerateOptionIndex = 0;
                activatedOptions.clear();
            }
        }
        String result = stringBuilder.toString();
        return result;
    }

    public static String string
            (int length, boolean containsCyrillic, boolean containsLatin, boolean containsNumeric, boolean containsSpaces,
             boolean containsSpecialSymbols) {
        return string(length, containsCyrillic, containsLatin, containsNumeric, containsSpaces,
                containsSpecialSymbols, false, false);
    }

    public static String numericString(int length) {
        return string(length, false, false, true,
                false, false);
    }

    public static String numericStringWithoutLeadingZeroes(int length) {
        String result = string(length, false, false, true,
                false, false);
        while (result.startsWith("0")) {
            result = string(length, false, false, true,
                    false, false);
        }
        return result;
    }

    public static String lettersString(int length, boolean cyrillic, boolean latin) {
        return string(length, cyrillic, latin, false,
                false, false);
    }

    public static String latinString(int length) {
        return string(length, false, true, false,
                false, false);
    }

    public static String cyrString(int length) {
        return string(length, true, false, false,
                false, false);
    }
}
