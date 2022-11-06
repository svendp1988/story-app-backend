package sven.projects.storyappbackend.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sven.projects.storyappbackend.model.Story;
import sven.projects.storyappbackend.service.StoryService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("story")
@CrossOrigin(originPatterns = {"http://localhost:*"})
public class StoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    private StoryService service;

    @GetMapping
    public Object getStories() {
        try {
            List<Story> stories = service.getStories();
            return new ResponseEntity<>(stories, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve stories", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Object createStory(@RequestParam(required = false) String storyId, @RequestBody CreateStoryResource... resources) {
        try {
            Set<Story> stories = service.saveStory(storyId, resources);
            return new ResponseEntity<>(stories, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Failed to save story.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
