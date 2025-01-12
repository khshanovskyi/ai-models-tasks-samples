package task_4;

import dto.Model;
import utils.OpenAIUtils;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;

public class AudioToText {

    public static void main(String[] args) throws IOException, InterruptedException {
        // NO need to change smth. This `main` method just to test how it works
        AudioToText audioToText = new AudioToText();
        String response = audioToText.callLLM();
        System.out.println(response);
    }

    public String callLLM() throws IOException, InterruptedException {
        // TODO: 1. load `codeus_audio.mp3` from the `resources` as `File`
        // TODO: 2. generate request body in the `byte[]` format, use `buildMultipartBody`
        // TODO:        - url:  https://api.openai.com/v1/audio/transcriptions
        // TODO:        - content type:  multipart/form-data; boundary=----OpenAIMultipartBoundary
        // TODO:        - requestBody: `HttpRequest.BodyPublishers.ofByteArray(requestBody)`
        // TODO: 3. return LLM response

        String boundary = "----OpenAIMultipartBoundary";
        ClassLoader classLoader = AudioToText.class.getClassLoader();
        File audioFile = new File(classLoader.getResource("codeus_audio.mp3").getFile());

        byte[] requestBody = buildMultipartBody(boundary, audioFile);

        return OpenAIUtils.call(
                "https://api.openai.com/v1/audio/transcriptions",
                "multipart/form-data; boundary=" + boundary,
                HttpRequest.BodyPublishers.ofByteArray(requestBody)
        );
    }


    /**
     * Builds the multipart request body.
     * <pre>
     *  curl --request POST \
     *   --url https://api.openai.com/v1/audio/transcriptions \
     *   --header "Authorization: Bearer $OPENAI_API_KEY" \
     *   --header 'Content-Type: multipart/form-data' \
     *   --form file=@/path/to/file/audio.mp3 \
     *   --form model=whisper-1
     * </pre>
     *
     * @param boundary  The boundary string for separating parts. (----OpenAIMultipartBoundary)
     * @param audioFile The audio file to include in the request.
     * @return The byte array of the complete request body.
     * @throws IOException If an error occurs while reading the file.
     */
    private static byte[] buildMultipartBody(String boundary, File audioFile) throws IOException {
        // NO need to change smth
        StringBuilder bodyBuilder = new StringBuilder();

        appendTextPart(bodyBuilder, boundary, "model", Model.WHISPER_1.getValue());

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
     * @param builder  The StringBuilder for the body.
     * @param boundary The boundary string.
     * @param name     The name of the parameter.
     * @param value    The value of the parameter.
     */
    private static void appendTextPart(StringBuilder builder, String boundary, String name, String value) {
        // NO need to change smth
        builder.append("--").append(boundary).append("\r\n")
                .append("Content-Disposition: form-data; name=\"").append(name).append("\"\r\n\r\n")
                .append(value).append("\r\n");
    }

    /**
     * Appends a file part to the multipart body.
     *
     * @param builder  The StringBuilder for the body.
     * @param boundary The boundary string.
     * @param name     The name of the file parameter.
     * @param fileName The file name.
     * @param mimeType The MIME type of the file.
     */
    private static void appendFilePart(StringBuilder builder, String boundary, String name, String fileName, String mimeType) {
        // NO need to change smth
        builder.append("--").append(boundary).append("\r\n")
                .append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(fileName).append("\"\r\n")
                .append("Content-Type: ").append(mimeType).append("\r\n\r\n");
    }
}
