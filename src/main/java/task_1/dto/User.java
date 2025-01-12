package task_1.dto;

import java.util.List;

public record User(String name, String surname, Integer age, List<String> hobbies) {

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", hobbies=" + hobbies +
                '}';
    }
}
