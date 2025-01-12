package dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    TEXT("text"),
    IMAGE_URL("image_url");

    private final String text;
    Type(final String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
