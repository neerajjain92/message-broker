package org.neeraj.queue.exception;

public class TopicDoesNotExistException extends RuntimeException {

    public TopicDoesNotExistException(final String topicName) {
        super(String.format("Topic %s does not exist. Please use existing topic", topicName));
    }
}
