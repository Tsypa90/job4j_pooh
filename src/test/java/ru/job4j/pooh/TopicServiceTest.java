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
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher));
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
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