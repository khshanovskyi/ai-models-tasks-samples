package task_5;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Model;
import utils.Constant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

public class TextToAudio {

    private static final String USER_PROMPT = """
            Seems that I'm going crazy!
            I've finished the last task from this session and would say that it was tough!
            'Codeus' community th best and I love morning sessions with deliberate practice!
            """;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException {
        String outputAudioPath = UUID.randomUUID() + ".mp3";

        new TextToAudio().generateAudioFromText(USER_PROMPT, outputAudioPath);
    }

    /**
     * Generates audio from text.
     *
     * <pre>
     *    curl https://api.openai.com/v1/audio/speech \
     *   -H "Authorization: Bearer $OPENAI_API_KEY" \
     *   -H "Content-Type: application/json" \
     *   -d '{
     *     "model": "tts-1",
     *     "input": "Today is a wonderful day to build something people love!",
     *     "voice": "alloy"
     *   }' \
     *   --output speech.mp3
     * </pre>
     */
    public void generateAudioFromText(String prompt, String fileName) throws IOException, InterruptedException {
        //TODO: 1. Create request body (use Map of values and write them as string via `ObjectMapper#writeValueAsString`)
        //TODO:     - model: `tts-1`
        //TODO:     - input: content that should be converted to speech
        //TODO:     - voice: `alloy`
        //TODO: 2. Call the method `call` (need to implement)
        //TODO: 3. Check if response successful (is 200)
        //TODO: 4. Save body to file (use method `saveAudioToFile`)

        String requestBody = mapper.writeValueAsString(
                Map.of(
                        "model", Model.TTS_1.getValue(),
                        "input", prompt,
                        "voice", "alloy"
                )
        );

        HttpResponse<byte[]> httpResponse = call(
                "https://api.openai.com/v1/audio/speech",
                HttpRequest.BodyPublishers.ofString(requestBody)
        );

        if (httpResponse.statusCode() == 200) {
            saveAudioToFile(httpResponse.body(), fileName);
            System.out.println("Audio generated and saved to: " + fileName);
        } else {
            System.err.println("Error generating audio: " + new String(httpResponse.body()));
        }
    }

    /**
     * Calls LLM and provides its response.
     *
     * @param url url to call LLM
     * @param bodyPublisher {@link java.net.http.HttpRequest.BodyPublisher} with request body
     * @return response from LLM in String format
     */
    public static HttpResponse<byte[]> call(String url, HttpRequest.BodyPublisher bodyPublisher) throws IOException, InterruptedException {
        // TODO: 1. Create `HttpRequest`
        // TODO:    - use builder to create `HttpRequest.newBuilder()`
        // TODO:    - provide URI `uri(...)`
        // TODO:    - provide header with Authorization token `header("Authorization", "Bearer " + Constant.API_KEY)`
        // TODO:    - provide header with Content-Type token `header("Content-Type", `application/json`)`
        // TODO:    - make POST request `POST(bodyPublisher)`
        // TODO:    - build HttpRequest
        // TODO: 2. Send request (it is sync, we will wait till LLM fully generates response)
        // TODO: 2.1. Use `HttpResponse.BodyHandlers.ofByteArray()` to collect content

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    private static void saveAudioToFile(byte[] audioData, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/audio/" + fileName)) {
            fos.write(audioData);
        }
    }
}
