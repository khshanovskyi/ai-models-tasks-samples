package task_3;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TextToImageTest {

    private final TextToImage textToImage = new TextToImage();

    private static final String USER_PROMPT = """
            Create a logo design featuring the word 'CODEUS_' in bold, pixelated font.
            1. It should have white background color.
            2. The text is black, except for the underscore at the end, which is bright green, resembling a command-line cursor.
            3. The text is enclosed within an open rectangular outline, where the left and top sides of the rectangle are visible.
            """;


    @Test
    void generateImage() throws IOException, InterruptedException {
        String fileName = UUID.randomUUID() + ".png";

        String imageUrl = textToImage.generateImage(USER_PROMPT);
        assertNotNull(imageUrl, "Provided image url cannot be null");
        assertTrue(imageUrl.startsWith("http"), "Provided image url does not start with `http`");

        textToImage.saveImageToFile(imageUrl, fileName);

        String outputPath = "src/main/resources/images/" + fileName;
        Path filePath = Paths.get(outputPath);
        assertTrue(Files.exists(filePath), "No file exists with path " + outputPath);
    }

}