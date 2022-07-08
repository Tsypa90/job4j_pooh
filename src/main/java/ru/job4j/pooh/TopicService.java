package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic =
            new ConcurrentHashMap<>();
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String EMPTY_STATUS = "204";
    private static final String OK_STATUS = "200";

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "501";
        var map = topic.get(req.getSourceName());
        if (POST.equals(req.httpRequestType())) {
            status = EMPTY_STATUS;
            if (map != null) {
                for (ConcurrentLinkedQueue<String> value : map.values()) {
                    value.add(req.getParam());
                }
                status = OK_STATUS;
            }
        } else if (GET.equals(req.httpRequestType())) {
                topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                topic.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                var linkedQueue = topic.get(req.getSourceName()).get(req.getParam());
                status = EMPTY_STATUS;
                text = linkedQueue.poll();
                if (text != null) {
                    status = OK_STATUS;
                }
            }
        return new Resp(text, status);
    }
}
