package sven.projects.storyappbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends MongoRepository<StoryEntry, String> {
    Optional<StoryEntry> findByText(String text);
}
