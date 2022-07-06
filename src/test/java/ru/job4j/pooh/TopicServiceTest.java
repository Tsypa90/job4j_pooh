package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TopicServiceTest {
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* Режим topic. Подписываемся на топик weather. client407. */
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
        /* Режим topic. Добавляем данные в топик weather. */
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher));
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565.
        Очередь отсутствует, т.к. еще не был подписан - получит пустую строку */
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2));
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is(""));
    }

    @Test
    public void whenPostButNoSubscribers() {
        TopicService topic = new TopicService();
        String paramForPublisher = "temperature=18";
        Resp result = topic.process(
                new Req("POST", "topic", "weather", paramForPublisher));
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
}