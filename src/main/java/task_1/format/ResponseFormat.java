package task_1.format;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains all required information about `response_format` for LLM request.
 */
public record ResponseFormat(Type type, @JsonProperty("json_schema") JsonSchema jsonSchema) {
}
