package webserver.http;

import com.google.common.base.Charsets;
import model.User;
import model.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;
    private User user;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        String line = bufferedReader.readLine();

        if (line == null) {
            return;
        }

        requestLine = RequestLine.parse(line);
        httpHeaders = new HttpHeaders(bufferedReader);

        httpBody = HttpBody.of(bufferedReader, httpHeaders.getContentLength());

        if (httpHeaders.hasContent()) {
            user = generateUser(httpBody.getQueryStringMap());
        }
    }

    private User generateUser(Map<String, String> queryString) {
        final String userId = queryString.get("userId");
        final String password = queryString.get("password");
        final String name = queryString.get("name");
        final String email = queryString.get("email");
        return new User(userId, password, name, email);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}
