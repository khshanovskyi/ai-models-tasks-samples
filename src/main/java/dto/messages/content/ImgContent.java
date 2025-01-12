package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImgContent extends Content {

    @JsonProperty("image_url")
    private final ImageUrl imageUrl;

    public ImgContent(String imageUrl) {
        super(Type.IMAGE_URL);
        this.imageUrl = new ImageUrl(imageUrl);
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }
}
