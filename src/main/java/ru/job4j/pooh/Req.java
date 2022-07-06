package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String TOPIC = "topic";

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String param = "";
        String sourceName = null;
        String poohMode = null;
        String httpRequest;
        var args = content.split(System.lineSeparator());
        var resp = args[0].split(" ");
        httpRequest = resp[0];
        if (POST.equals(httpRequest)) {
            param = args[args.length - 1];
            var respSep = resp[1].split("/");
            poohMode = respSep[1];
            sourceName = respSep[2];
        } else if (GET.equals(httpRequest)) {
            var respSep = resp[1].split("/");
            poohMode = respSep[1];
            sourceName = respSep[2];
            if (TOPIC.equals(poohMode)) {
                param = respSep[3];
            }
        }
        return new Req(httpRequest, poohMode, sourceName, param);
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
