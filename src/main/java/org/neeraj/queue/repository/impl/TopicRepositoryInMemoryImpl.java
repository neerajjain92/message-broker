package org.neeraj.queue.repository.impl;

import org.neeraj.queue.exception.TopicAlreadyExistException;
import org.neeraj.queue.exception.TopicDoesNotExistException;
import org.neeraj.queue.handler.TopicHandler;
import org.neeraj.queue.model.Topic;
import org.neeraj.queue.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TopicRepositoryInMemoryImpl implements TopicRepository {

    private final Map<String, TopicHandler> topicHandlers;
    private Logger LOGGER = LoggerFactory.getLogger(TopicRepositoryInMemoryImpl.class);

    public TopicRepositoryInMemoryImpl() {
        this.topicHandlers = new HashMap<>();
    }

    @Override
    public void createTopic(final TopicHandler topicHandler) {
        LOGGER.info("Topic {} saved successfully.", topicHandler.getTopic());
        final String topicName = topicHandler.getTopic().getName();
        if(topicHandlers.containsKey(topicName)) {
            throw new TopicAlreadyExistException("Topic ");
        }
        topicHandlers.put(topicName, topicHandler);
    }

    @Override
    public Topic getTopic(final String topicName) throws TopicDoesNotExistException {
        if(!topicHandlers.containsKey(topicName)) {
            throw new TopicDoesNotExistException(topicName);
        }
        return topicHandlers.get(topicName).getTopic();
    }

    @Override
    public TopicHandler getTopicHandler(final String topicName) throws TopicDoesNotExistException {
        if(!topicHandlers.containsKey(topicName)) {
            throw new TopicDoesNotExistException(topicName);
        }
        return topicHandlers.get(topicName);
    }
}
