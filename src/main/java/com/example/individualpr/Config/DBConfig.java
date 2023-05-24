package com.example.individualpr.Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBConfig {//присваение значений из аплекайшена для дальнейшего использования

    public static String username = "";

    public static String password = "";
    public static String dbname = "";
    public static String host = "";

    private static String properties = "src/main/resources/application.properties";// путь на апликайшйен
    public static void  initField() {
        Pattern patternHost = Pattern.compile("\\{MYSQL_HOST:(.+)\\}", Pattern.MULTILINE);// регулярное выражение
        Pattern patternDBName = Pattern.compile("\\d/(.+)$", Pattern.MULTILINE);


        Matcher hostMatcher = patternHost.matcher(loadProp("spring.datasource.url"));
        Matcher dbNameMatcher = patternDBName.matcher(loadProp("spring.datasource.url"));

        hostMatcher.find();
        dbNameMatcher.find();

        String host = hostMatcher.group(1);
        String dbname = dbNameMatcher.group(1);

        String dbUser = loadProp("spring.datasource.username");
        String dbPass = loadProp("spring.datasource.password");

        DBConfig.username = dbUser;
        DBConfig.password = dbPass;
        DBConfig.dbname = dbname;
        DBConfig.host = host;
    }

    private static String loadProp(String name) {//загружает свойства из файла апликайшен
        Properties props = new Properties();
        try (FileInputStream propStream = new FileInputStream(properties)) {
            props.load(propStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty(name);//Возвращает значение свойства (строку) по ключу
    }


}