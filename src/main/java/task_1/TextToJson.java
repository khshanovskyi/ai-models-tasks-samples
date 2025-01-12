import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import dto.messages.request.Message;
import dto.Model;
import dto.messages.Role;
import dto.messages.content.TxtContent;
import dto.messages.response.ai.ChatCompletion;
import dto.messages.response.format.JsonSchema;
import dto.messages.response.format.ResponseFormat;
import dto.messages.response.format.Type;
import dto.response.User;
import dto.response.Users;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextToJson {

    private final static String txt = """
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
               **Hobbies:** Baking, yoga, and blogging about travel. \s
               **Notes:** Regular traveler with a clean history. No red flags detected in preliminary checks. \s
            
            5. **Name:** David Alexander \s
               **Surname:** Lee \s
               **Age:** 36 \s
               **Hobbies:** Reading, cycling, and bird watching. \s
               **Notes:** Subject has been cooperative during prior inquiries. No anomalies found in routine behavior. \s
            
            This information has been collated for reference purposes and remains strictly confidential. For further inquiries, contact the investigation lead.
            
            **Prepared by:** \s
            Officer Jane Reynolds \s
            Badge No. 88902 \s
            
            **End of Report** \s
            """;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(
                new Message(
                        Role.SYSTEM,
                        List.of(
                                new TxtContent("You need to extract user data to json.")
                        )
                )
        );
        messages.add(
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent(txt)
                        )
                )
        );

        JsonNode invoiceSchema = generateInvoiceSchema();

        ResponseFormat responseFormat = new ResponseFormat(
                Type.JSON_SCHEMA,
                new JsonSchema("userSchema", invoiceSchema)
        );

        OpenAIClient openAIClient = new OpenAIClient(
                Constant.OPEN_AI_API_URI.toString(),
                Constant.API_KEY,
                "application/json",
                Model.GPT_4o
        );

        String response = openAIClient.call(messages, responseFormat);

        System.out.println("OpenAI Response: " + response);
    }

    private static JsonNode generateInvoiceSchema() {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_2020_12,
                OptionPreset.PLAIN_JSON
        );
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);

        return generator.generateSchema(Users.class);
    }
}
