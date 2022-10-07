package ru.job4j.pooh;

/**
 * Интерфейс описывает сущность сервис
 */
public interface Service {

    String POST = "POST";
    String GET = "GET";

    /** Метод описывает работу сервиса
     * @param req - входящий запрос в виде объекта Req
     * @return - Объект Resp - ответ сервиса
     */
    Resp process(Req req);
}
