package model.request;

import webserver.http.*;

import java.util.Map;

public class RequestLine {

    private static final String DELIMITER = " ";
    private static final int INDEX_METHOD = 0;
    private static final int INDEX_PATH = 1;
    private static final int INDEX_PROTOCOL = 2;

    private final Method method;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(Method method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String request) {
        final String[] requestElements = request.split(DELIMITER);

        final Method method = Method.from(requestElements[INDEX_METHOD]);
        final Path path = Path.from(requestElements[INDEX_PATH]);
        final Protocol protocol = new Protocol(requestElements[INDEX_PROTOCOL]);

        return new RequestLine(method, path, protocol);
    }

    public Method getMethod() {
        return method;
    }

    public String getPathValue() {
        return path.getValue();
    }

    public Map<String, String> getQueryString() {
        return path.getQueryStringMap();
    }

    public Type getProtocolType() {
        return protocol.getType();
    }

    public Version getProtocolVersion() {
        return protocol.getVersion();
    }
}
