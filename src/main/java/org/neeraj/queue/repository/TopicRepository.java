package org.neeraj.queue.repository;

import org.neeraj.queue.exception.TopicAlreadyExistException;
import org.neeraj.queue.exception.TopicDoesNotExistException;
import org.neeraj.queue.handler.TopicHandler;
import org.neeraj.queue.model.Topic;

public interface TopicRepository {

    void createTopic(final TopicHandler topicHandler) throws TopicAlreadyExistException;

    Topic getTopic(final String topicName) throws TopicDoesNotExistException;

    TopicHandler getTopicHandler(final String topicName) throws TopicDoesNotExistException;
}
