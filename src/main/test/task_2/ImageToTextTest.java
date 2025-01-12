package task_2;

import dto.messages.Role;
import dto.messages.content.ImgContent;
import dto.messages.content.TxtContent;
import dto.messages.request.Message;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageToTextTest {

    private ImageToText imageToText = new ImageToText();

    @Test
    void base64Scenario() throws IOException, InterruptedException {
        String response = imageToText.callLLM(
                getMessages(String.format("data:image/jpeg;base64, %s", Base64.getEncoder().encodeToString(getPicture())))
        );
        System.out.println(response);

        assertNotNull(response, "No response was returned");

        String content = imageToText.getContent(response);
        System.out.println(content);
        assertTrue(content.contains("CODEUS"));
    }

    private static byte[] getPicture() throws IOException {
        // NO need to change smth.
        ClassLoader classLoader = ImageToText.class.getClassLoader();
        File file = new File(classLoader.getResource("codeus_merch.jpg").getFile());
        InputStream inputStream = new FileInputStream(file);
        return inputStream.readAllBytes();
    }

    @Test
    void urlScenario() throws IOException, InterruptedException {
        String response = imageToText.callLLM(
                getMessages("https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/African_Bush_Elephant.jpg/1440px-African_Bush_Elephant.jpg")
        );
        System.out.println(response);

        assertNotNull(response, "No response was returned");

        String content = imageToText.getContent(response);
        System.out.println(content);
        assertTrue(content.contains("elephant"));
    }

    private static List<Message> getMessages(String url) throws IOException {
        return List.of(
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent("What is present on this image?"),
                                new ImgContent(url)
                        )
                )
        );
    }

}