package org.neeraj.queue.handler;

import lombok.Getter;
import lombok.SneakyThrows;
import org.neeraj.queue.model.Message;
import org.neeraj.queue.model.Topic;
import org.neeraj.queue.model.TopicSubscriber;

@Getter
public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(final Topic topic, final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while (currOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }
                final Message message = topic.getMessages().get(currOffset);
                topicSubscriber.getSubscriber().consume(message);

                // Let's not simply increment the offset by 1, since subscriber can reset the offset
                // while subscribing as well, so we should compare it with original offset
                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
            } while (true);
        }
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
