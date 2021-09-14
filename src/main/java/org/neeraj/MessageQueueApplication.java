package org.neeraj;

import org.neeraj.queue.controller.QueueController;
import org.neeraj.queue.model.Message;
import org.neeraj.queue.model.SleepingSubscriber;

/**
 * Hello world!
 */
public class MessageQueueApplication {

    public static void main(String[] args) throws InterruptedException {
        final QueueController queueController = new QueueController();
        queueController.createTopic("topic1");
        queueController.createTopic("topic2");

        final SleepingSubscriber sleepingSub1 = new SleepingSubscriber("sub1", 1000);
        final SleepingSubscriber sleepingSub2 = new SleepingSubscriber("sub2", 1000);

        queueController.subscribe(sleepingSub1, "topic1");
        queueController.subscribe(sleepingSub2, "topic1");

        final SleepingSubscriber sleepingSub3 = new SleepingSubscriber("sub3", 5000);
        queueController.subscribe(sleepingSub3, "topic2");

        queueController.publish("topic1", new Message("Message-1"));
        queueController.publish("topic1", new Message("Message-2"));
        queueController.publish("topic2", new Message("Message-3"));

        Thread.sleep(15000);
        queueController.publish("topic2", new Message("Message-4"));
        queueController.publish("topic1", new Message("Message-5"));

        queueController.resetOffset("topic1", sleepingSub1, 0);
    }
}
