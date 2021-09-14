package org.neeraj.queue.service;

import lombok.NonNull;
import org.neeraj.queue.exception.TopicAlreadyExistException;
import org.neeraj.queue.exception.TopicDoesNotExistException;
import org.neeraj.queue.model.ISubscriber;
import org.neeraj.queue.model.Message;
import org.neeraj.queue.model.Topic;

public interface TopicService {

    Topic createTopic(@NonNull final String topicName) throws TopicAlreadyExistException;

    void subscribe(@NonNull final String topicName, @NonNull final ISubscriber subscriber) throws TopicDoesNotExistException;

    void publish(String topicName, Message message) throws TopicDoesNotExistException;

    void resetOffset(String topicName, ISubscriber subscriber, Integer newOffset) throws TopicDoesNotExistException;
}
