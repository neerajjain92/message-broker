package org.neeraj.queue.model;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Topic {

    private final String name;
    private final String id;
    private final List<Message> messages;
    private final List<TopicSubscriber> subscribers;
    private Logger LOGGER = LoggerFactory.getLogger(Topic.class);

    public Topic(final String name, final String id) {
        this.name = name;
        this.id = id;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        LOGGER.info("{} instantiated successfully", this);
    }

    public synchronized void addMessage(@NonNull final Message message) {
        this.messages.add(message);
    }

    public void addSubscriber(@NonNull final TopicSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public String toString() {
        return "Topic {" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
