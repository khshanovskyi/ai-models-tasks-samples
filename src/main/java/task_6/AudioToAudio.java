package task_6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Model;
import dto.messages.Role;
import dto.messages.content.AudioContent;
import dto.messages.content.TxtContent;
import dto.messages.request.Message;
import utils.Constant;
import utils.OpenAIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AudioToAudio {

    private static final String USER_PROMPT = "What is in this recording?";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        // NO need to change smth. This `main` method just to test how it works
        AudioToAudio audioToAudio = new AudioToAudio();
        String response = audioToAudio.callLLM(getMessages(getAudio()));

        System.out.println("OpenAI Response: " + response);

        String content = audioToAudio.getContent(response);
        saveAudio(content, UUID.randomUUID().toString() + ".mp3");
    }

    public String callLLM(List<Message> messages) throws IOException, InterruptedException {
        // TODO: 1. Create request object node (implement method `collectRequestNode()`)
        // TODO: 2. Call OpenAIUtils.call(...)
        // TODO:        - url: Constant.BASE_OPEN_AI_URL + "/chat/completions"
        // TODO:        - content type: "application/json"
        // TODO:        - requestBody: HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request))
        // TODO: 3. Return response from the LLM

        throw new RuntimeException("Not implemented yet");
    }

    private static ObjectNode collectRequestNode(List<Message> messages) {
        // TODO: 1. Create a new ObjectNode using mapper
        // TODO: 2. Set the "model" to Model.GPT_4o_AUDIO_PREVIEW.getValue()
        // TODO: 3. Set the "modalities" to List.of("text", "audio")
        // TODO: 4. Set the "audio" parameter with voice="alloy" and format="mp3"
        // TODO: 5. Set the "messages" parameter with the provided messages
        // TODO: 6. Return the completed request node

        throw new RuntimeException("Not implemented yet");
    }

    public String getContent(String llmResponse) throws JsonProcessingException {
        // TODO: 1. Parse the LLM response using ObjectMapper
        // TODO: 2. Navigate to the audio data at path "/choices/0/message/audio/data"
        // TODO: 3. Return the audio data as text or null if not found

        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Provides list of {@link Message}. Message with USER_PROMPT.
     */
    private static List<Message> getMessages(byte[] audioBytes) {
        //TODO: Add message that contains text content with USER_PROMPT and audio Base64 content and `.mp3` format
        //hint: DTOs have been already created, see AudioContent

        throw new RuntimeException("Not implemented yet");
    }

    public static byte[] getAudio() throws IOException {
        // NO need to change smth.
        ClassLoader classLoader = AudioToAudio.class.getClassLoader();
        File file = new File(classLoader.getResource("codeus_user_audio.mp3").getFile());
        InputStream inputStream = new FileInputStream(file);
        return inputStream.readAllBytes();
    }

    public static void saveAudio(String data, String fileName) throws IOException {
        // NO need to change smth.
        String outputAudioFilePath = String.format("src/main/resources/audio/%s", fileName);
        byte[] audioBytes = Base64.getDecoder().decode(data);
        try (FileOutputStream fos = new FileOutputStream(outputAudioFilePath)) {
            fos.write(audioBytes);
        }
        System.out.println("Audio file saved successfully: " + outputAudioFilePath);
    }
}
