package task_1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import dto.messages.request.Message;
import dto.Model;
import dto.messages.Role;
import dto.messages.content.TxtContent;
import dto.messages.response.ai.ChatCompletion;
import task_1.format.JsonSchema;
import task_1.format.ResponseFormat;
import task_1.format.Type;
import task_1.dto.Users;
import utils.Constant;
import utils.OpenAIUtils;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

public class TextToJson {


    private final static String SYSTEM_PROMPT = """
            You will be provided with text that provides some information about people. You need to collect user data to JSON.
            In case if there are no information about people, you need return empty json.
            """;

    private final static String USER_PROMPT = """
            **Internal Investigation Department** \s
            **Subject: Personal Background Verification** \s
            **Date: January 12, 2025** \s
            
            **Report No. 452-AJ-2025** \s
            
            Following an inquiry into individuals connected to the recent operation, the following profiles have been verified and documented:
            
            1. **Name:** John Carter \s
               **Surname:** Smith \s
               **Age:** 34 \s
               **Hobbies:** Fishing, woodworking, and hiking. \s
               **Notes:** Subject maintains a low-risk profile. No significant activity requiring further investigation. \s
            
            2. **Name:** Emily Rose \s
               **Surname:** Peterson \s
               **Age:** 27 \s
               **Hobbies:** Photography, painting, and volunteering at animal shelters. \s
               **Notes:** Known for her artistic pursuits. No prior incidents of concern reported. \s
            
            3. **Name:** Michael Anthony \s
               **Surname:** Johnson \s
               **Age:** 42 \s
               **Hobbies:** Gardening, playing chess, and marathon running. \s
               **Notes:** Frequent participant in local community events. Cleared from prior involvement in any suspicious activity. \s
            
            4. **Name:** Sophia Maria \s
               **Surname:** Hernandez \s
               **Age:** 29 \s
               **Relatives:** Not found \s
               **Hobbies:** Baking, yoga, and blogging about travel. \s
               **Notes:** Regular traveler with a clean history. No red flags detected in preliminary checks. \s
            
            5. **Name:** David Alexander \s
               **Surname:** Lee \s
               **Age:** 36 \s
               **Relatives:** Not found \s
               **Hobbies:** Reading, cycling, and bird watching. \s
               **Notes:** Subject has been cooperative during prior inquiries. No anomalies found in routine behavior. \s
            
            This information has been collated for reference purposes and remains strictly confidential. For further inquiries, contact the investigation lead.
            
            **Prepared by:** \s
            Officer Jane Reynolds \s
            Badge No. 88902 \s
            
            **End of Report** \s
            """;

    private final static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        // NO need to change smth. This `main` method just to test how it works
        TextToJson textToJson = new TextToJson();
        String response = textToJson.callLLM(getMessages());

        System.out.println("OpenAI Response: " + response);

        textToJson.getUsersFromLLMResponse(response).users().forEach(System.out::println);
    }

    /**
     * Collects information about users.
     *
     * <pre>
     * curl https://api.openai.com/v1/chat/completions \
     *   -H "Content-Type: application/json" \
     *   -H "Authorization: Bearer $OPENAI_API_KEY" \
     *   -d '{
     *     "model": "gpt-4o-2024-08-06",
     *     "messages": [
     *       {
     *         "role": "developer",
     *         "content": "You extract email addresses into JSON data."
     *       },
     *       {
     *         "role": "user",
     *         "content": "Feeling stuck? Send a message to help@mycompany.com."
     *       }
     *     ],
     *     "response_format": {
     *       "type": "json_schema",
     *       "json_schema": {
     *         "name": "email_schema",
     *         "schema": {
     *             "type": "object",
     *             "properties": {
     *                 "email": {
     *                     "description": "The email address that appears in the input",
     *                     "type": "string"
     *                 }
     *             },
     *             "additionalProperties": false
     *         }
     *       }
     *     }
     *   }'
     * </pre>
     * <a href="https://platform.openai.com/docs/guides/text-generation?text-generation-quickstart-example=json">Detailed info</a>
     *
     * @return full LLM response JSON
     */
    public String callLLM(List<Message> messages) throws IOException, InterruptedException {
        // TODO: 1. Generate JSON schema of the Users.class (implement method `generateUsersJsonSchema()`)
        // TODO: 2. Prepare ResponseFormat (as an name for schema use `userSchema`)
        // TODO: 3. Prepare ObjectNode request body (with messages and ResponseFormat) (implement method `collectRequestNode()`)
        // TODO: 4. Call OpenAIUtils.call(...)
        // TODO:        - url:  https://api.openai.com/v1/chat/completions
        // TODO:        - content type:  application/json
        // TODO:        - requestBody:  convert json node to string via `writeValueAsString()` and use `HttpRequest.BodyPublishers.ofString(...)`

        throw new RuntimeException("Not implemented yet");
    }

    private ObjectNode collectRequestNode(List<Message> messages, ResponseFormat responseFormat) {
        // TODO: 1. Create instance of ObjectNode from ObjectMapper `mapper.createObjectNode()`
        // TODO: 2. PUT `model`, I would recommend GPT-4o
        // TODO: 3. SET `messages`, (use ObjectMapper to convert them to Tree)
        // TODO: 4. SET `response_format`, (use ObjectMapper to convert it to Tree)

        throw new RuntimeException("Not implemented yet");
    }

    public Users getUsersFromLLMResponse(String llmResponse) throws JsonProcessingException {
        // TODO: 1. Map value from `llmResponse` to ChatCompletion `mapper.readValue(llmResponse, ChatCompletion.class)`
        // TODO: 2. Get its `content`
        // TODO: 3. If it instance of String and starts with '{' and ends with '}', then map it `Users`
        // TODO: 4. return `null` by default

        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Provides list of {@link Message}. First message with SYSTEM_PROMPT, second with USER_PROMPT.
     */
    private static List<Message> getMessages() {
        throw new RuntimeException("Not implemented yet");
    }

    private JsonNode generateUsersJsonSchema() {
        // TODO: Get Json Schema of `Users.class`
        // TODO: https://github.com/victools/jsonschema-generator
        // TODO: If you scroll documentation to down of victools, it will provide you with example how to do that

        throw new RuntimeException("Not implemented yet");
    }

}
