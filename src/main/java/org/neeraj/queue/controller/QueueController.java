package org.neeraj.queue.controller;

import lombok.NonNull;
import org.neeraj.queue.exception.TopicAlreadyExistException;
import org.neeraj.queue.exception.TopicDoesNotExistException;
import org.neeraj.queue.model.ISubscriber;
import org.neeraj.queue.model.Message;
import org.neeraj.queue.model.Topic;
import org.neeraj.queue.model.TopicSubscriber;
import org.neeraj.queue.service.TopicService;
import org.neeraj.queue.service.impl.TopicServiceImpl;

public class QueueController {

    private TopicService topicService;

    public QueueController() {
        this.topicService = new TopicServiceImpl();
    }

    /**
     * Create topic in Message Queue
     *
     * @param topicName Name of the topic
     * @return {@link Topic} instance
     * @throws TopicAlreadyExistException if Topic does not exist
     */
    public Topic createTopic(@NonNull final String topicName) throws TopicAlreadyExistException {
        return topicService.createTopic(topicName);
    }

    /**
     * Contract for {@link ISubscriber} to subscribe a {@link Topic}
     *
     * @param subscriber {@link ISubscriber} instance who is subscribing to the topic
     * @param topicName  {@link Topic#name} name of topic being subscribed.
     * @throws TopicDoesNotExistException if Topic does not exist
     */
    public void subscribe(@NonNull final ISubscriber subscriber, @NonNull final String topicName)
            throws TopicDoesNotExistException {
        topicService.subscribe(topicName, subscriber);
    }

    /**
     * Contract for publishing {@link Message} to a {@link Topic}
     *
     * @param topicName Name of {@link Topic} to which message is being published.
     * @param message   {@link Message} instance being published to topic.
     * @throws TopicDoesNotExistException if Topic does not exist
     */
    public void publish(@NonNull final String topicName, @NonNull final Message message)
            throws TopicDoesNotExistException {
        topicService.publish(topicName, message);
    }

    /**
     * Contract for resetting the {@link TopicSubscriber#offset} offset for {@link ISubscriber}
     *
     * @param topicName  Name of {@link Topic}  for which
     * @param subscriber {@link ISubscriber} who is resetting the offset
     * @param newOffset  New offset value for the {@link ISubscriber}
     * @throws TopicDoesNotExistException
     */
    public void resetOffset(@NonNull final String topicName, @NonNull final ISubscriber subscriber,
                            @NonNull final Integer newOffset) throws TopicDoesNotExistException {
        topicService.resetOffset(topicName, subscriber, newOffset);
    }
}
