package dto.messages.response.ai;

public record Choice(
        int index,
        ChatCompletion.Message message,
        Object logprobs,
        String finishReason
) {}