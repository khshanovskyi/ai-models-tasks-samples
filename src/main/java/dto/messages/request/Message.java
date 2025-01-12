package dto.messages.request;

import dto.messages.Role;
import dto.messages.content.Content;

import java.util.List;

public record Message(Role role, List<Content> content) {
}
