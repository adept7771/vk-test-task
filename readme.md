что реализовано

тест параметризован
ретрай аналайзер навешен в примитивной форме
конфигурируется фреймворк и из файла и из system variables
имеется проверка тестовых данных
есть тестовый генератор
все вынесено в степы и пейджи

по кейсам - сделал пока три кейса с 3 типами данных подгружаемыми из библиотеки ВК. 
+ кейс с подгрузкой фотки с компа. Адрес папки задан в сеттингах. И в сьют файле. Для работы теста
  надо поменять пути (сейчас там абсолютные пути, надо докручивать механизм относительного пути)
+ кейс с загрузкой видео. Адреса видео так же пока абсолютные. Проверка достаточно первичная.
наборы данных можно поменять в suite файле. 
Запускать так же из идеи по кнопке Ран на сьюте. Или просто в мавене жмем mvn test и вперед. 
В пом файле так же помечено какой сьют файл юзать. 
В сьют файле закомменчены те кейсы которые пока не реализованы.

тесты в мавене запускаем mvn test
Аллюр отчет запускаем через mvn io.qameta.allure:allure-maven:serve

По проекту много косяков о которых мне известно или не известно. Спешил.
