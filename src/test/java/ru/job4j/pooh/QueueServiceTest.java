package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod));
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null));
        assertThat(result.text(), is("temperature=18"));
        assertThat(result.status(), is("200"));
    }

    @Test
    public void whenGetThenGetNull() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null));
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }

    @Test
    public void whenPostThenTwiceGetThenNull() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod));
        queueService.process(
                new Req("GET", "queue", "weather", null));
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null));
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
}