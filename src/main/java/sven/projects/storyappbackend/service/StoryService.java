package sven.projects.storyappbackend.service;

import org.bson.types.ObjectId;
import sven.projects.storyappbackend.api.CreateStoryResource;
import sven.projects.storyappbackend.model.Story;

import java.util.List;
import java.util.Set;

public interface StoryService {

    List<Story> getStories();
    Story getStoryById(String id);
    Set<Story> saveStory(String storyId, CreateStoryResource... resources);

}
