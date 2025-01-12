package dto.messages.response.format;

import com.fasterxml.jackson.databind.JsonNode;

public record JsonSchema(String name, JsonNode schema) {
}
