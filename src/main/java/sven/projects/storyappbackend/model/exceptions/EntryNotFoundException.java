package sven.projects.storyappbackend.model.exceptions;

public class EntryNotFoundException extends RuntimeException {

    public EntryNotFoundException(String message) {
        super(message);
    }

}
