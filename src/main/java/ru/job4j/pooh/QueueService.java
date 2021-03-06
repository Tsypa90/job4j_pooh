package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String EMPTY_STATUS = "204";
    private static final String OK_STATUS = "200";

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "501";
        var linkedQueue = queue.get(req.getSourceName());
        if (GET.equals(req.httpRequestType())) {
            status = EMPTY_STATUS;
            if (linkedQueue != null && !linkedQueue.isEmpty()) {
                text = linkedQueue.poll();
                status = OK_STATUS;
            }
        } else if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            text = req.getParam();
            queue.get(req.getSourceName()).add(text);
            status = OK_STATUS;
        }
        return new Resp(text, status);
    }
}
