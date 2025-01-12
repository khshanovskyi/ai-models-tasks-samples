import dto.messages.content.ImgContent;
import dto.messages.request.Message;
import dto.Model;
import dto.messages.Role;
import dto.messages.content.TxtContent;
import utils.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ImageToText {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClassLoader classLoader = ImageToText.class.getClassLoader();
        File file = new File(classLoader.getResource("codeus_merch.jpg").getFile());
        InputStream inputStream = new FileInputStream(file);
        byte[] imageBytes = inputStream.readAllBytes();

        Message message = new Message(
                Role.USER,
                List.of(
                        new TxtContent(
                                "What is present on this message?"
                        ),
                        new ImgContent(
                                String.format("data:image/jpeg;base64, %s", Base64.getEncoder().encodeToString(imageBytes))
                        )
                )
        );
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        OpenAIClient openAIClient = new OpenAIClient(
                "https://api.openai.com/v1/chat/completions",
                Constant.API_KEY,
                "application/json",
                Model.GPT_4o
        );
        String resp = openAIClient.call(messages);
        System.out.println(resp);
    }

}
