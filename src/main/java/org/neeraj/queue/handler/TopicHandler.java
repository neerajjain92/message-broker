package org.neeraj.queue.handler;

import lombok.Getter;
import lombok.NonNull;
import org.neeraj.queue.model.Topic;
import org.neeraj.queue.model.TopicSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TopicHandler {

    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;
    private final Logger LOGGER = LoggerFactory.getLogger(TopicHandler.class);

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber subscriber : topic.getSubscribers()) {
            startSubscriberWorker(subscriber);
        }
    }

    public void startSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();
        if (!subscriberWorkers.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
            subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        } else {
            LOGGER.info("Subscriber {} already started, so let's wake it up if in sleep state...", subscriberId);
            final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
            subscriberWorker.wakeUpIfNeeded();
        }
    }
}
