package sven.projects.storyappbackend.api;

public class CreateStoryResource extends StoryResource {

    private String text;
    private String imageUrl;

    public CreateStoryResource() {
    }

    public CreateStoryResource(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
