package dto.messages.content;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    TEXT("text"),
    IMAGE_URL("image_url"),
    INPUT_AUDIO("input_audio");

    private final String text;
    Type(final String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
