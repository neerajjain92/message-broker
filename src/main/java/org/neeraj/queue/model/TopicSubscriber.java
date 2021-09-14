package org.neeraj.queue.model;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Topic Subscriber with offset capability.
 */
@Getter
public class TopicSubscriber {

    private final AtomicInteger offset;
    private final ISubscriber subscriber;

    public TopicSubscriber(final ISubscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }
}
