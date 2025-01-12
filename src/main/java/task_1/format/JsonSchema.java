package task_1.format;

import com.fasterxml.jackson.databind.JsonNode;

public record JsonSchema(String name, JsonNode schema) {
}
