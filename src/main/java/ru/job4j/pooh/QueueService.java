package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Реализация сервиса, работает в режиме "queue"
 * Потребители получают данные из одной очереди
 *  @author Goridov Dmitriy
 *  @version 1.0
 */
public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    /**
     * @param req - запрос к сервису в виде объекта Req
     * @return - ответ сервиса в виде объекта Resp
     */
    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "200";
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).offer(req.getParam());

        } else if (GET.equals(req.httpRequestType())) {
            text = queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
        }

        if (text == null) {
            text = "";
            status = "204";
        }
        return new Resp(text, status);
    }
}
