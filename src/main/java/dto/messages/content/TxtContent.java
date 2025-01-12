package dto;

public class TxtContent extends Content {

    private final String text;

    public TxtContent(String text) {
        super(Type.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
