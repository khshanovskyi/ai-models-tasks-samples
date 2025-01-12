package utils;

import java.net.URI;

public final class Constant {
    private Constant() {}

    public static final URI OPEN_AI_API_URI = URI.create("https://api.openai.com/v1/chat/completions");

    public static final String DEFAULT_SYSTEM_PROMPT = "You are an assistant who answers concisely and informatively.";

    public static final String API_KEY = "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";

}
