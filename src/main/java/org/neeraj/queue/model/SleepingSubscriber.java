package org.neeraj.queue.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepingSubscriber implements ISubscriber {

    private final String id;
    private final int sleepTimeInMillis;
    private Logger LOGGER = LoggerFactory.getLogger(SleepingSubscriber.class);

    public SleepingSubscriber(final String id, final int sleepTimeInMillis) {
        this.id = id;
        this.sleepTimeInMillis = sleepTimeInMillis;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consume(final Message message) throws InterruptedException {
        LOGGER.info("Subscriber: {} started consuming: {}", getId(), message.getMessage());
        Thread.sleep(sleepTimeInMillis);
        LOGGER.info("Subscriber: {} consumed: {}", getId(), message.getMessage());
    }
}
