package task_6;

import dto.messages.Role;
import dto.messages.content.AudioContent;
import dto.messages.content.TxtContent;
import dto.messages.request.Message;
import org.junit.jupiter.api.Test;
import task_5.TextToAudio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioToAudioTest {

    private final AudioToAudio audioToAudio = new AudioToAudio();

    @Test
    void generateAudioToAudio() throws IOException, InterruptedException {
        String fileName = UUID.randomUUID() + ".mp3";
        List<Message> messages = List.of(
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent("What user did?"),
                                new AudioContent(
                                        AudioToAudio.getAudio(),
                                        "mp3"
                                )
                        )
                )
        );

        String response = audioToAudio.callLLM(messages);
        String content = audioToAudio.getContent(response);
        AudioToAudio.saveAudio(content, fileName);

        String outputPath = "src/main/resources/audio/" + fileName;
        Path filePath = Paths.get(outputPath);
        assertTrue(Files.exists(filePath), "No file exists with path " + outputPath);
    }


}