package dto.messages.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AudioContent extends Content {

    @JsonProperty("input_audio")
    private final InputAudio inputAudio;

    public AudioContent(byte[] inputAudio, String format) {
        super(Type.INPUT_AUDIO);
        this.inputAudio = new InputAudio(inputAudio, format);
    }

    public InputAudio getInputAudio() {
        return inputAudio;
    }
}
