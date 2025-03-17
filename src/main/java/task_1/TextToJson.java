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
     *         "role": "system",
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
        JsonNode usersJsonSchema = generateUsersJsonSchema();
        ResponseFormat responseFormat = new ResponseFormat(
                Type.JSON_SCHEMA,
                new JsonSchema("userSchema", usersJsonSchema)
        );

        ObjectNode request = collectRequestNode(messages, responseFormat);

        System.out.println("Request: \n" +mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
        System.out.println();

        return OpenAIUtils.call(
                Constant.BASE_OPEN_AI_URL + "/chat/completions",
                "application/json",
                HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request))
        );
    }

    private ObjectNode collectRequestNode(List<Message> messages, ResponseFormat responseFormat) {
        ObjectNode request = mapper.createObjectNode();
        request.put("model", Model.GPT_4o_MINI.getValue());
        request.set("messages", mapper.valueToTree(messages));
        request.set("response_format", mapper.valueToTree(responseFormat));
        return request;
    }

    public Users getUsersFromLLMResponse(String llmResponse) throws JsonProcessingException {
        ChatCompletion chatCompletion = mapper.readValue(llmResponse, ChatCompletion.class);
        Object content = chatCompletion.choices().getFirst().message().content();
        if (content instanceof String strContent) {
            if (strContent.startsWith("{") && strContent.endsWith("}")) {
                return mapper.readValue(strContent, Users.class);
            }
        }
        return null;
    }

    /**
     * Provides list of {@link Message}. First message with SYSTEM_PROMPT, second with USER_PROMPT.
     */
    private static List<Message> getMessages() {
        return List.of(
                new Message(Role.SYSTEM, List.of(new TxtContent(SYSTEM_PROMPT))),
                new Message(Role.USER, List.of(new TxtContent(USER_PROMPT)))
        );
    }

    private JsonNode generateUsersJsonSchema() {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        return generator.generateSchema(Users.class);
    }

}
