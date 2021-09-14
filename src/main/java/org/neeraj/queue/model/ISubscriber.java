package org.neeraj.queue.model;

public interface ISubscriber {

    String getId();

    void consume(Message message) throws InterruptedException;

}
