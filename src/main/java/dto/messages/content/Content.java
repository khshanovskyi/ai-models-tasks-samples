package dto.messages.content;

public abstract class Content {
    protected Type type;

    public Content(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
