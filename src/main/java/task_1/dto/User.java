package dto.response;

import java.util.List;

public record User(String name, String surname, Integer age, List<String> hobbies) {
}
