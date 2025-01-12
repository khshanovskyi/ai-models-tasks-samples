package dto.messages.response.format;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {

    JSON_SCHEMA("json_schema");

    private final String type;
    Type(final String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
