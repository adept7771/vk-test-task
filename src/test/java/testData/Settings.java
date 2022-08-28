package testData;

import java.util.ArrayList;

public enum Settings {

    // Тестовые настройки можно и через property файлы грузить, но мне честно не особо нравится что там
    // длинные конструкции кода на обработку настроек получаются. Опять же можно прикрутить что-то
    // стороннее типа Owner.

    testAddress("https://vk.com"),
    needToCheckTestData("true"), // проверять ли тестовые данные перед запуском?
    timeToWaitElementMS("3000"),
    browser("chrome"), // браузер по умолчанию chrome, firefox, opera
    startMaximized("true"); // true false

    public String val;

    Settings(String val) {
        this.val = val;
    }

    public static ArrayList<String> getNames(){
        Settings[] values=values();
        ArrayList<String>names=new ArrayList<>();
        for(Settings setting:values){
            names.add(setting.name());
        }
        return names;
    }
}