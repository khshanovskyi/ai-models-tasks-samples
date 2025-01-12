package task_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Model;
import utils.Constant;
import utils.OpenAIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

public class TextToImage {

    private static final String USER_PROMPT = """
            Create a logo design featuring the word 'CODEUS_' in bold, pixelated font.
            1. It should have white background color.
            2. The text is black, except for the underscore at the end, which is bright green, resembling a command-line cursor.
            3. The text is enclosed within an open rectangular outline, where the left and top sides of the rectangle are visible.
            """;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        // NO need to change smth. This `main` method just to test how it works
        TextToImage textToImage = new TextToImage();
        String imageName = UUID.randomUUID() + ".png";

        String imageUrl = textToImage.generateImage(USER_PROMPT);
        System.out.println(imageUrl);

        if (imageUrl != null) {
            textToImage.saveImageToFile(imageUrl, imageName);
            System.out.println("Image saved to: " + imageName);
        } else {
            System.err.println("Failed to generate the image.");
        }
    }

    /**
     * Generates picture by user prompt.
     *
     * @return picture url.
     */
    public String generateImage(String prompt) throws IOException, InterruptedException {
        // TODO: 1. Create request body (implement method `generateRequestBody()`)
        // TODO: 2. Call OpenAIUtils.call(...)
        // TODO:        - url:  https://api.openai.com/v1/images/generations
        // TODO:        - content type:  application/json
        // TODO:        - requestBody: `HttpRequest.BodyPublishers.ofString(requestBody)`
        // TODO: 3. Retrieve response body.
        // TODO: 4. Get `data`.`url` via ObjectMapper (`mapper.readTree(response)`)
        // TODO: 5. Return Image url or null

        String requestBody = generateRequestBody(prompt);

        String response = OpenAIUtils.call(
                "https://api.openai.com/v1/images/generations",
                "application/json",
                HttpRequest.BodyPublishers.ofString(requestBody)
        );

        if (response != null) {
            JsonNode jsonResponse = mapper.readTree(response);
            JsonNode dataNode = jsonResponse.get("data");
            if (dataNode != null && dataNode.isArray() && !dataNode.isEmpty()) {
                return dataNode.get(0).get("url").asText();
            }
        }

        return null;
    }

    /**
     * <pre>
     *  curl https://api.openai.com/v1/images/generations \
     *   -H "Content-Type: application/json" \
     *   -H "Authorization: Bearer $OPENAI_API_KEY" \
     *   -d '{
     *     "model": "dall-e-3",
     *     "prompt": "a white siamese cat",
     *     "n": 1,
     *     "size": "1024x1024"
     *   }'
     * </pre>
     */
    private String generateRequestBody(String prompt) throws JsonProcessingException {
        //TODO: 1. Use ObjectMapper to writeValueAsString
        //TODO: 2. Create map of:
        //TODO:     - model: use dall-e
        //TODO:     - prompt: provided prompt
        //TODO:     - n: 1 (we need only one image)
        //TODO:     - size: size (image size)
        return mapper.writeValueAsString(
                Map.of(
                        "model", Model.DALL_E_3.getValue(),
                        "prompt", prompt,
                        "n", 1,
                        "size", "1024x1024"
                ));
    }


    public void saveImageToFile(String imageUrl, String fileName) throws IOException {
        // NO need to change smth.
        if (imageUrl == null || !imageUrl.startsWith("http")) {
            throw new IllegalArgumentException("Invalid image url: " + imageUrl);
        }

        String outputPath = "src/main/resources/images/" + fileName;
        Path newFilePath = Paths.get(outputPath);
        Path parentDir = newFilePath.getParent();

        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        if (!Files.exists(newFilePath)) {
            Files.createFile(newFilePath);
        }

        try (InputStream inputStream = new URL(imageUrl).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}
