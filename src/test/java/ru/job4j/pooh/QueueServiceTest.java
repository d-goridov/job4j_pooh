package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=11";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=11"));
    }

    @Test
    public void whenManyPostThenGetAll() {
        QueueService queueService = new QueueService();
        String param1 = "temperature=18";
        String param2 = "temperature=-5";
        String param3 = "temperature=24";
        queueService.process(
                new Req("POST", "queue", "weather", param1)
        );
        queueService.process(
                new Req("POST", "queue", "weather", param2)
        );
        queueService.process(
                new Req("POST", "queue", "weather", param3)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result3 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );

        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=-5"));
        assertThat(result3.text(), is("temperature=24"));
    }

    @Test
    public void whenPostThenGetQueueWithNotParam() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
}