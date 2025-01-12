package dto.messages.response.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.messages.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(
        Role role,
        Object content,
        Object refusal
) {}