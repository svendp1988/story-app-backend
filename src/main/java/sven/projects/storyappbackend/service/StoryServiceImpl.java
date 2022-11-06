package sven.projects.storyappbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sven.projects.storyappbackend.api.CreateStoryResource;
import sven.projects.storyappbackend.model.Story;
import sven.projects.storyappbackend.model.exceptions.EntryNotFoundException;
import sven.projects.storyappbackend.repository.StoryEntry;
import sven.projects.storyappbackend.repository.StoryRepository;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static sven.projects.storyappbackend.model.Story.*;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository repository;

    @Override
    public List<Story> getStories() {
        List<StoryEntry> all = repository.findAll();
        return all.stream()
                .map(Story::fromDbModel)
                .collect(toList());
    }

    @Override
    public Story getStoryById(String id) {
        Optional<StoryEntry> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new EntryNotFoundException("Could not find entry with id: " + id);
        }

        return fromDbModel(byId.get());
    }

    @Override
    public Set<Story> saveStory(String storyId, CreateStoryResource... resources) {
        Set<Story> stories = new HashSet<>();
        StoryEntry entry;

        if (storyId != null) {
            Story storyById = getStoryById(storyId);
            for (CreateStoryResource resource : resources) {
                if (storyById.canAddOption()) {
                    entry = repository.save(toDbModel(fromResource(resource)));
                    storyById.addOption(entry.getId());
                    stories.add(storyById);
                    stories.add(fromDbModel(entry));
                }
            }
            repository.save(toDbModel(storyById));
        } else {
            for (CreateStoryResource resource : resources) {
                entry = repository.save(toDbModel(fromResource(resource)));
                stories.add(fromDbModel(entry));
            }
        }

        return stories;
    }

}
