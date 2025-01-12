package dto.messages.response.format;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseFormat(Type type, @JsonProperty("json_schema") JsonSchema jsonSchema) {
}
