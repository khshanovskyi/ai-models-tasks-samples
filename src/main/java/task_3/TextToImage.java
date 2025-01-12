import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class TextToImage {

    public static void main(String[] args) throws IOException, InterruptedException {
        String prompt = "A futuristic cityscape with flying cars under a pink sky.";
        String outputImagePath = "generated_image.png";

        String imageUrl = generateImage(prompt);

        if (imageUrl != null) {
            saveImageToFile(imageUrl, outputImagePath);
            System.out.println("Image saved to: " + outputImagePath);
        } else {
            System.err.println("Failed to generate the image.");
        }
    }

    public static String generateImage(String prompt) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(Map.of(
                "prompt", prompt,
                "n", 1,
                "size", "1024x1024"
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/images/generations"))
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode jsonResponse = mapper.readTree(response.body());
            JsonNode dataNode = jsonResponse.get("data");
            if (dataNode != null && dataNode.isArray() && dataNode.size() > 0) {
                return dataNode.get(0).get("url").asText();
            }
        }

        System.err.println("Error: " + response.body());
        return null;
    }

    public static void saveImageToFile(String imageUrl, String outputPath) throws IOException {
        try (InputStream inputStream = new URL(imageUrl).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
