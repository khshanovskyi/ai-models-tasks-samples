package dto;

public enum Model {
    GPT_35_TURBO("gpt-3.5-turbo"),
    GPT_4o_MINI("gpt-4o-mini"),
    GPT_4o("gpt-4o"),
    DALL_E_3("dall-e-3"),
    WHISPER_1("whisper-1"),
    TTS_1("tts-1"),
    GPT_4o_AUDIO_PREVIEW("gpt-4o-audio-preview");

    private final String value;

    Model(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
