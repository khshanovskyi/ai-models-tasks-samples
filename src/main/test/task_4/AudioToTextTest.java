package task_4;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AudioToTextTest {

    private AudioToText audioToText = new AudioToText();

    @Test
    void checkCodeusAudioTranscription() throws IOException, InterruptedException {
        String response = audioToText.callLLM();
        assertNotNull(response, "Provided response cannot be null");
        assertTrue(response.contains("you have successfully finished a task from"), "Part `you have successfully finished a task from` is absent in response");
        assertTrue(response.contains("community with AI text recognition"), "Part `community with AI text recognition` is absent in response");
    }

}