package ru.job4j.pooh;

/**
 * Класс парсит входящий запрос
 * и возвращает его в виде объекта Req
 * @author Goridov Dmitriy
 * @version 1.0
 */
public class Req {
    /**
     * httpRequestType - тип запроса: может быть GET или POST.
     * poohMode - указывает на режим работы: queue или topic.
     * sourceName - имя очереди или топика.
     * param - содержимое запроса.
     */
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * Метод парсит входящий запрос в виде строки,
     * и отдает необходимые данные в виде массива
     * @param content - запрос
     * @return - массив
     */
    private static String[] parse(String content) {
        String ls = System.lineSeparator();
        String[] rsl = new String[4];
        String[] contentStrings = content.split("/", 4);
        String[] nameSource = contentStrings[2].split(" ");
        rsl[0] = contentStrings[0].trim();
        rsl[1] = contentStrings[1];
        rsl[2] = nameSource[0];
        String[] param = contentStrings[3].split(ls);

        if ("POST".equals(rsl[0]) && ("queue".equals(rsl[1]) || "topic".equals(rsl[1]))) {
            rsl[3] = param[param.length - 1];
        }

        if ("GET".equals(rsl[0]) && "queue".equals(rsl[1])) {
            rsl[3] = "";
        }

        if ("GET".equals(rsl[0]) && "topic".equals(rsl[1])) {
            String[] firstParam = param[0].split(" ");
            rsl[3] = firstParam[0];
        }
        return rsl;
    }

    public static Req of(String content) {
        String[] rsl = parse(content);
        return new Req(rsl[0], rsl[1], rsl[2], rsl[3]);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
