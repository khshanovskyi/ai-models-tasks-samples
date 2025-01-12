import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.messages.request.Message;
import dto.Model;
import task_1.format.ResponseFormat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class OpenAIClient {

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    private final String url;
    private final String apiKey;
    private final String contentType;
    private final Model model;

    public OpenAIClient(String url, String apiKey, String contentType, Model model) {
        this.url = url;
        this.apiKey = apiKey;
        this.contentType = contentType;
        this.model = model;
        httpClient = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public String call(List<Message> messages) throws IOException, InterruptedException {
        ObjectNode request = mapper.createObjectNode();
        request.put("model", model.getValue());
        request.set("messages", mapper.valueToTree(messages));

        return request(request);
    }

    public String call(List<Message> messages, ResponseFormat responseFormat) throws IOException, InterruptedException {
        ObjectNode request = mapper.createObjectNode();
        request.put("model", model.getValue());
        request.set("messages", mapper.valueToTree(messages));
        request.set("response_format", mapper.valueToTree(responseFormat));

        return request(request);
    }

    private String request(ObjectNode request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request)))
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofLines()).body().collect(Collectors.joining());
    }


}
