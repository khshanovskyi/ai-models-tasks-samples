import utils.Constant;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class AudioToText {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClassLoader classLoader = AudioToText.class.getClassLoader();
        File audioFile = new File(classLoader.getResource("codeus_audio.mp3").getFile());

        String boundary = "----OpenAIMultipartBoundary";
        byte[] requestBody = buildMultipartBody(boundary, audioFile);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/audio/transcriptions"))
                .header("Authorization", "Bearer " + Constant.API_KEY)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    /**
     * Builds the multipart request body.
     *
     * @param boundary The boundary string for separating parts.
     * @param audioFile The audio file to include in the request.
     * @return The byte array of the complete request body.
     * @throws IOException If an error occurs while reading the file.
     */
    private static byte[] buildMultipartBody(String boundary, File audioFile) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();

        appendTextPart(bodyBuilder, boundary, "model", "whisper-1");

        String mimeType = Files.probeContentType(audioFile.toPath());
        appendFilePart(bodyBuilder, boundary, "file", audioFile.getName(), mimeType);

        byte[] fileContent = Files.readAllBytes(audioFile.toPath());

        byte[] bodyPrefix = bodyBuilder.toString().getBytes();
        String bodySuffix = "\r\n--" + boundary + "--\r\n";
        byte[] bodySuffixBytes = bodySuffix.getBytes();

        byte[] requestBody = new byte[bodyPrefix.length + fileContent.length + bodySuffixBytes.length];
        System.arraycopy(bodyPrefix, 0, requestBody, 0, bodyPrefix.length);
        System.arraycopy(fileContent, 0, requestBody, bodyPrefix.length, fileContent.length);
        System.arraycopy(bodySuffixBytes, 0, requestBody, bodyPrefix.length + fileContent.length, bodySuffixBytes.length);

        return requestBody;
    }

    /**
     * Appends a text part to the multipart body.
     *
     * @param builder The StringBuilder for the body.
     * @param boundary The boundary string.
     * @param name The name of the parameter.
     * @param value The value of the parameter.
     */
    private static void appendTextPart(StringBuilder builder, String boundary, String name, String value) {
        builder.append("--").append(boundary).append("\r\n")
                .append("Content-Disposition: form-data; name=\"").append(name).append("\"\r\n\r\n")
                .append(value).append("\r\n");
    }

    /**
     * Appends a file part to the multipart body.
     *
     * @param builder The StringBuilder for the body.
     * @param boundary The boundary string.
     * @param name The name of the file parameter.
     * @param fileName The file name.
     * @param mimeType The MIME type of the file.
     */
    private static void appendFilePart(StringBuilder builder, String boundary, String name, String fileName, String mimeType) {
        builder.append("--").append(boundary).append("\r\n")
                .append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(fileName).append("\"\r\n")
                .append("Content-Type: ").append(mimeType).append("\r\n\r\n");
    }
}
