package task_5;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TextToAudioTest {

    private final TextToAudio textToAudio = new TextToAudio();

    @Test
    void generateImage() throws IOException, InterruptedException {
        String fileName = UUID.randomUUID() + ".mp3";

        textToAudio.generateAudioFromText("Speach test. One, two, three...", fileName);

        String outputPath = "src/main/resources/audio/" + fileName;
        Path filePath = Paths.get(outputPath);
        assertTrue(Files.exists(filePath), "No file exists with path " + outputPath);
    }
}