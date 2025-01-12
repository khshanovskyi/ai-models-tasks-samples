import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Constant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TextToAudio {

    public static void main(String[] args) throws IOException, InterruptedException {
        String text = "Today is a wonderful day to build something people love!";
        String outputAudioPath = "speech.mp3";

        generateAudioFromText(text, outputAudioPath);
    }

    public static void generateAudioFromText(String text, String outputPath) throws IOException, InterruptedException {
        String url = "https://api.openai.com/v1/audio/speech";

        BodyContent bodyContent = new BodyContent("tts-1", text, "alloy");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(bodyContent)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            saveAudioToFile(response.body(), outputPath);
            System.out.println("Audio generated and saved to: " + outputPath);
        } else {
            System.err.println("Error generating audio: " + new String(response.body()));
        }
    }

    private static void saveAudioToFile(byte[] audioData, String outputPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(audioData);
        }
    }

    private record BodyContent(String model, String input, String voice) {

    }
}
