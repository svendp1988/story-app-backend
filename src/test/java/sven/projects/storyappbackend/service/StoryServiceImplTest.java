package sven.projects.storyappbackend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sven.projects.storyappbackend.api.CreateStoryResource;
import sven.projects.storyappbackend.model.Story;
import sven.projects.storyappbackend.model.exceptions.EntryNotFoundException;
import sven.projects.storyappbackend.repository.StoryEntry;
import sven.projects.storyappbackend.repository.StoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoryServiceImplTest {

    @Autowired
    private StoryService service;
    @Autowired
    private StoryRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getStoryById_throwsException() {
        EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, () -> service.getStoryById("unknown id"));
        assertEquals("Could not find entry with id: unknown id", exception.getMessage());
    }

    @Test
    void saveStory_noDependencies() {
        Set<Story> stories = service.saveStory(null, createResource("Test entry", "Test imageUrl"));
        assertThat(stories).hasSize(1);

        Story story = stories.stream().findFirst().get();
        assertEquals("Test entry", story.getText());
        assertEquals("Test imageUrl", story.getImageUrl());

        String storyId = story.getId();
        Optional<StoryEntry> byId = repository.findById(storyId);
        assertTrue(byId.isPresent());

        StoryEntry actual = byId.get();
        assertEquals(actual.getId(), story.getId());
        assertEquals(actual.getText(), story.getText());
        assertEquals(actual.getImageUrl(), story.getImageUrl());
        assertEquals(actual.getOptions(), story.getOptions());
    }

    @Test
    void saveStory_withDependencies() {
        Set<Story> stories = service.saveStory(null, createResource("Test entry", "Test imageUrl"));
        assertThat(stories).hasSize(1);

        String storyId = stories.stream().findFirst().get().getId();
        service.saveStory(storyId, createResource("Entry 1", "ImageUrl 1"), createResource("Entry 2", "ImageUrl2"));
        Optional<StoryEntry> byText1 = repository.findByText("Entry 1");
        Optional<StoryEntry> byText2 = repository.findByText("Entry 2");
        assertTrue(byText1.isPresent());
        assertTrue(byText2.isPresent());

        StoryEntry mainEntry = repository.findById(storyId).get();
        assertThat(mainEntry.getOptions()).containsExactlyInAnyOrder(byText1.get().getId(), byText2.get().getId());
    }

    static CreateStoryResource createResource(String text, String imageUrl) {
        return new CreateStoryResource(text, imageUrl);
    }
}
