package dto.messages;

import dto.messages.content.Content;

import java.util.List;

public record Message(Role role, List<Content> content) {
}
