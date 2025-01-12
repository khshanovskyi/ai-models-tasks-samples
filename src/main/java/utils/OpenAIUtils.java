package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class OpenAIUtils {

    private OpenAIUtils() {}

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Calls LLM and provides its response.
     *
     * @param url url to call LLM
     * @param contentType [application/json, ...]
     * @param bodyPublisher {@link java.net.http.HttpRequest.BodyPublisher} with request body
     * @return response from LLM in String format
     */
    public static String call(String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) throws IOException, InterruptedException {
        // TODO: 1. Create `HttpRequest`
        // TODO:    - use builder to create `HttpRequest.newBuilder()`
        // TODO:    - provide URI `uri(...)`
        // TODO:    - provide header with Authorization token `header("Authorization", "Bearer " + Constant.API_KEY)`
        // TODO:    - provide header with Content-Type token `header("Content-Type", contentType)`
        // TODO:    - make POST request `POST(bodyPublisher)`
        // TODO:    - build HttpRequest
        // TODO: 2. Send request (it is sync, we will wait till LLM fully generates response)
        // TODO: 2.1. Use `HttpResponse.BodyHandlers.ofString()` to collect content
        // TODO: 3. return response body

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .header("Content-Type", contentType)
                .POST(bodyPublisher)
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
    }
}
