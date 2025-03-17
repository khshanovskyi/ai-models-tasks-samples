package task_2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Model;
import dto.messages.content.ImgContent;
import dto.messages.request.Message;
import dto.messages.Role;
import dto.messages.content.TxtContent;
import dto.messages.response.ai.ChatCompletion;
import utils.Constant;
import utils.OpenAIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.List;

public class ImageToText {

    private static final String USER_PROMPT = "What is present on this image?";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        // NO need to change smth. This `main` method just to test how it works
        ImageToText imageToText = new ImageToText();

        byte[] imageBytes = getPicture();
        String llmResponse = imageToText.callLLM(getMessages(imageBytes));
        System.out.println(llmResponse);

        String content = imageToText.getContent(llmResponse);
        System.out.println(content);
    }

    public String callLLM(List<Message> messages) throws IOException, InterruptedException {
        // TODO: 1. Prepare ObjectNode request body (with messages and ResponseFormat) (implement method `collectRequestNode()`)
        // TODO: 2. Call OpenAIUtils.call(...)
        // TODO:        - url:  https://api.openai.com/v1/chat/completions
        // TODO:        - content type:  application/json
        // TODO:        - requestBody:  convert json node to string via `writeValueAsString()` and use `HttpRequest.BodyPublishers.ofString(...)`

        ObjectNode request = collectRequestNode(messages);
        System.out.println("Request: \n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
        System.out.println();
        return OpenAIUtils.call(
                Constant.BASE_OPEN_AI_URL + "/chat/completions",
                "application/json",
                HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request))
        );
    }

    public String getContent(String llmResponse) throws JsonProcessingException {
        // TODO: 1. Map value from `llmResponse` to ChatCompletion `mapper.readValue(llmResponse, ChatCompletion.class)`
        // TODO: 2. Get its `content`
        // TODO: 3. If it instance of String then return it
        // TODO: 4. return `null` by default

        ChatCompletion chatCompletion = mapper.readValue(llmResponse, ChatCompletion.class);

        Object content = chatCompletion.choices().getFirst().message().content();
        if (content instanceof String strContent) {
            return strContent;
        }

        return null;
    }

    private static ObjectNode collectRequestNode(List<Message> messages) {
        // TODO: 1. Create instance of ObjectNode from ObjectMapper `mapper.createObjectNode()`
        // TODO: 2. PUT `model`, I would recommend GPT-4o
        // TODO: 3. SET `messages`, (use ObjectMapper to convert them to Tree)

        ObjectNode request = mapper.createObjectNode();
        request.put("model", Model.GPT_4o.getValue());
        request.set("messages", mapper.valueToTree(messages));
        return request;
    }

    /**
     * Provides list with request Messages.
     * <b>Pay attention on the message structure.</b>
     *
     * <pre>
     *   {
     *     "model": "gpt-4o-mini",
     *     "messages": [
     *       {
     *         "role": "user",
     *         "content": [
     *           {
     *             "type": "text",
     *             "text": "What is in this image?"
     *           },
     *           {
     *             "type": "image_url",
     *             "image_url": {
     *                  "url": f"data:image/jpeg;base64,{base64_image}"
     *             }
     *           }
     *         ]
     *       }
     *     ],
     *     "max_tokens": 300
     *   }
     * </pre>
     */
    private static List<Message> getMessages(byte[] imageBytes) {
        //TODO: Add message that contains text content with USER_PROMPT and image content
        //hint: DTOs have been already created, see TxtContent and ImgContent
        //hint: also you can test with link to picture instead of Base64 format

        return List.of(
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent(USER_PROMPT),
                                new ImgContent(
                                        String.format("data:image/jpeg;base64, %s", Base64.getEncoder().encodeToString(imageBytes))
                                )
                        )
                )
        );
    }

    private static byte[] getPicture() throws IOException {
        // NO need to change smth.
        ClassLoader classLoader = ImageToText.class.getClassLoader();
        File file = new File(classLoader.getResource("codeus_merch.jpg").getFile());
        InputStream inputStream = new FileInputStream(file);
        return inputStream.readAllBytes();
    }

}
