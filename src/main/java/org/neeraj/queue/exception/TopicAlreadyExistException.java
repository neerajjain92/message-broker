package org.neeraj.queue.exception;

public class TopicAlreadyExistException extends RuntimeException {

    public TopicAlreadyExistException(final String topicName) {
        super(String.format("Topic %s already exist. Please use different name", topicName));
    }
}
