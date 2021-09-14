package org.neeraj.queue.service.impl;

import lombok.NonNull;
import org.neeraj.queue.exception.TopicAlreadyExistException;
import org.neeraj.queue.exception.TopicDoesNotExistException;
import org.neeraj.queue.handler.TopicHandler;
import org.neeraj.queue.model.ISubscriber;
import org.neeraj.queue.model.Message;
import org.neeraj.queue.model.Topic;
import org.neeraj.queue.model.TopicSubscriber;
import org.neeraj.queue.repository.TopicRepository;
import org.neeraj.queue.repository.impl.TopicRepositoryInMemoryImpl;
import org.neeraj.queue.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TopicServiceImpl.class);

    public TopicServiceImpl() {
        this.topicRepository = new TopicRepositoryInMemoryImpl();
    }

    @Override
    public Topic createTopic(@NonNull final String topicName) throws TopicAlreadyExistException {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        final TopicHandler topicHandler = new TopicHandler(topic);
        topicRepository.createTopic(topicHandler);
        return topic;
    }

    @Override
    public void subscribe(final @NonNull String topicName,
                          final @NonNull ISubscriber subscriber) throws TopicDoesNotExistException {
        final Topic topic = topicRepository.getTopic(topicName);
        topic.addSubscriber(new TopicSubscriber(subscriber));
        LOGGER.info("Subscriber {} subscribed to topic {}#{}", subscriber.getId(), topicName, topic.getId());
    }

    @Override
    public void publish(final String topicName, final Message message) {
        final Topic topic = topicRepository.getTopic(topicName);
        topic.addMessage(message);
        LOGGER.info("Message {}, published to topic {}", message.getMessage(), topicName);
        new Thread(() -> topicRepository.getTopicHandler(topicName).publish()).start();
    }

    @Override
    public void resetOffset(final String topicName, final ISubscriber subscriber,
                            final Integer newOffset) throws TopicDoesNotExistException {
        final Topic topic = topicRepository.getTopic(topicName);
        for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffset().set(newOffset);
                LOGGER.info("Topic Subscriber's {} offset reset to {} from {}", subscriber.getId(),
                        topicSubscriber.getOffset().get(), newOffset);
                new Thread(() -> topicRepository.getTopicHandler(topicName).
                        startSubscriberWorker(topicSubscriber)).start();
            }
        }
    }
}
