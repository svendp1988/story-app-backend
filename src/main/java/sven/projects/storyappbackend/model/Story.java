package sven.projects.storyappbackend.model;

import sven.projects.storyappbackend.api.CreateStoryResource;
import sven.projects.storyappbackend.repository.StoryEntry;

import java.util.ArrayList;
import java.util.List;

public final class Story {

    private String id;
    private String text;
    private String imageUrl;
    private List<String> options;

    public Story() {
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean canAddOption() {
        return options == null || options.size() < 2;
    }

    public void addOption(String storyId) {
        if (options == null) {
            options = new ArrayList<>(2);
        }
        options.add(storyId);
    }

    public static StoryEntry toDbModel(Story story) {
        StoryEntry entry = new StoryEntry();
        entry.setId(story.id);
        entry.setText(story.text);
        entry.setImageUrl(story.imageUrl);
        entry.setOptions(story.options);

        return entry;
    }

    public static Story fromDbModel(StoryEntry entry) {
        Story story = new Story();
        story.id = entry.getId();
        story.text = entry.getText();
        story.imageUrl = entry.getImageUrl();
        story.options = entry.getOptions();

        return story;
    }

    public static Story fromResource(CreateStoryResource resource) {
        Story story = new Story();
        story.text = resource.getText();
        story.imageUrl = resource.getImageUrl();

        return story;
    }
}
