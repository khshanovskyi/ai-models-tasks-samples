package dto.messages.response.ai;

import java.util.List;

public record ChatCompletion(
        String id,
        String object,
        long created,
        String model,
        List<Choice> choices,
        Usage usage,
        String serviceTier,
        String systemFingerprint
) {

    public record Message(
            String role,
            String content,
            Object refusal
    ) {}

    public record Usage(
            int promptTokens,
            int completionTokens,
            int totalTokens,
            TokenDetails promptTokensDetails,
            TokenDetails completionTokensDetails
    ) {}

    public record TokenDetails(
            int cachedTokens,
            int audioTokens,
            int reasoningTokens,
            int acceptedPredictionTokens,
            int rejectedPredictionTokens
    ) {}

    public record Content(
            List<Person> persons
    ) {}

    public record Person(
            String name,
            String surname,
            int age,
            List<String> hobbies
    ) {}
}

