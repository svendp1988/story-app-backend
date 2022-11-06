package sven.projects.storyappbackend.repository;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class StoryEntry {

    private String id;
    private String text;
    private String imageUrl;
    private List<String> options;



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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
