package task_1;

import dto.messages.Role;
import dto.messages.content.TxtContent;
import dto.messages.request.Message;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import task_1.dto.Users;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TextToJsonTest {

    private final TextToJson textToJson = new TextToJson();
    private static final Message SYSTEM_MESSAGE = new Message(
            Role.SYSTEM,
            List.of(
                    new TxtContent("""
                            You will be provided with text that provides some information about people. You need to collect user data to JSON.
                            In case if there are no information about people, you need return empty json.
                            """)
            )
    );

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    **Metropolitan Police Department** \s
                    **Subject: Background Profiles** \s
                    **Date: January 12, 2025** \s
                    
                    **Report No. 783-BX-2025** \s
                    
                    In response to the ongoing inquiry, the following individuals have been reviewed and their personal details confirmed: \s
                    
                    1. **Name:** Olivia Grace \s
                       **Surname:** Williams \s
                       **Age:** 31 \s
                       **Hobbies:** Gardening, writing poetry, and practicing meditation. \s
                       **Notes:** Subject is a private individual with no recorded history of concerning activity. Frequently participates in local literary events. \s
                    
                    2. **Name:** Ethan James \s
                       **Surname:** Miller \s
                       **Age:** 39 \s
                       **Hobbies:** Camping, rock climbing, and restoring vintage motorcycles. \s
                       **Notes:** Known for his adventurous hobbies. Active member of a local mountaineering club. No anomalies found during checks. \s
                    
                    3. **Name:** Isabella Rose \s
                       **Surname:** Martin \s
                       **Age:** 25 \s
                       **Hobbies:** Dancing, learning languages, and photography. \s
                       **Notes:** Subject is a recent graduate with a clean record. Active on social media platforms, primarily showcasing travel and photography. \s
                    
                    This information is provided as part of the verification process and is classified under departmental guidelines. Further updates will follow if necessary. \s
                    
                    **Prepared by:** \s
                    Detective Samuel Carter \s
                    Badge No. 42156 \s
                    
                    **End of Report** \s
                    """,
            """
                    **Central Investigation Bureau** \s
                    **Subject: Financial and Personal Background Check** \s
                    **Date: January 12, 2025** \s
                    
                    **Report No. 912-FIN-2025** \s
                    
                    As part of the compliance and risk management initiative, the following profiles have been verified:
                    
                    1. **Name:** Daniel Thomas \s
                       **Surname:** White \s
                       **Age:** 38 \s
                       **Occupation:** Software Engineer \s
                       **Annual Income:** $95,000 \s
                       **Account Status:** Active, no overdrafts in the past 12 months. \s
                       **Hobbies:** Investing, playing tennis, and photography. \s
                       **Notes:** Regular savings contributor with a steady transaction history. No suspicious financial behavior detected.
                    
                    2. **Name:** Chloe Amelia \s
                       **Surname:** Brown \s
                       **Age:** 29 \s
                       **Occupation:** Marketing Specialist \s
                       **Annual Income:** $72,000 \s
                       **Account Status:** Active, with a credit card carrying an average balance of $3,200. \s
                       **Hobbies:** Traveling, cooking, and yoga. \s
                       **Notes:** Moderate credit utilization. History of on-time loan repayments. No financial anomalies observed. \s
                    
                    3. **Name:** Liam Alexander \s
                       **Surname:** Green \s
                       **Age:** 44 \s
                       **Occupation:** Small Business Owner \s
                       **Annual Income:** $120,000 \s
                       **Account Status:** Active, with a business account showing consistent deposits. \s
                       **Hobbies:** Golfing, mentoring entrepreneurs, and reading financial literature. \s
                       **Notes:** Subject has been flagged for high-value transfers to overseas accounts, all of which were documented and compliant with regulatory norms. \s
                    
                    These profiles have been reviewed for risk assessment purposes and will remain under observation for any irregular activities. All information is confidential and will only be disclosed as per legal and regulatory requirements. \s
                    
                    **Prepared by:** \s
                    Officer Megan Wright \s
                    Badge No. 30014 \s
                    
                    **End of Report** \s
                    """
    })
    void successfulCases(String data) throws IOException, InterruptedException {
        List<Message> messages = List.of(
                SYSTEM_MESSAGE,
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent(data)
                        )
                )
        );
        String response = textToJson.callLLM(messages);
        assertNotNull(response, "No response was returned");

        Users usersFromLLMResponse = textToJson.getUsersFromLLMResponse(response);
        assertNotNull(usersFromLLMResponse, "No response was returned");
        assertEquals(3, usersFromLLMResponse.users().size(), "There should be three users");
    }


    @ParameterizedTest
    @ValueSource(strings = {
            """
                    Sup, what can u do?
                    """,
            """
                    **National Business Oversight Division** \s
                    **Subject: Corporate Background and Financial Assessment** \s
                    **Date: January 12, 2025** \s
                    
                    **Report No. 573-CORP-2025** \s
                    
                    Following the routine corporate compliance check, the following companies have been reviewed: \s
                    
                    1. **Company Name:** Tech Innovators LLC \s
                       **Industry:** Software Development \s
                       **Established:** 2010 \s
                       **Annual Revenue:** $18,500,000 \s
                       **Employee Count:** 120 \s
                       **Account Status:** Active, with consistent cash flow and no overdue liabilities. \s
                       **Key Activities:** Development of cloud-based applications and AI-driven solutions. \s
                       **Notes:** Subject company demonstrates stable growth and maintains a strong credit score. No recent disputes or legal concerns detected. \s
                    
                    2. **Company Name:** GreenLeaf Organics Inc. \s
                       **Industry:** Agriculture and Food Production \s
                       **Established:** 2015 \s
                       **Annual Revenue:** $5,200,000 \s
                       **Employee Count:** 65 \s
                       **Account Status:** Active, with a small business loan of $250,000 currently under repayment. \s
                       **Key Activities:** Production and distribution of organic fruits, vegetables, and related products. \s
                       **Notes:** Loan repayment history is on track. However, recent transactions indicate delays in payments to suppliers. Recommended for closer monitoring. \s
                    
                    3. **Company Name:** Urban Builders Co. \s
                       **Industry:** Construction and Real Estate Development \s
                       **Established:** 2003 \s
                       **Annual Revenue:** $32,000,000 \s
                       **Employee Count:** 210 \s
                       **Account Status:** Active, with a line of credit of $1,500,000 fully utilized. \s
                       **Key Activities:** Large-scale urban development projects, including residential and commercial buildings. \s
                       **Notes:** Subject has shown increased financial strain due to project delays. Review of ongoing projects is advised to assess potential risks. \s
                    """
    })
    void zeroMappingCases(String data) throws IOException, InterruptedException {
        List<Message> messages = List.of(
                SYSTEM_MESSAGE,
                new Message(
                        Role.USER,
                        List.of(
                                new TxtContent(data)
                        )
                )
        );
        String response = textToJson.callLLM(messages);
        assertNotNull(response, "No response was returned");

        System.out.println(response);

        Users usersFromLLMResponse = textToJson.getUsersFromLLMResponse(response);
        if (usersFromLLMResponse != null) {
            assertNotNull(usersFromLLMResponse, "No response was returned");
            if (usersFromLLMResponse.users() != null) {
                assertEquals(0, usersFromLLMResponse.users().size(), "There should be 0 users");
            }
        }
    }
}