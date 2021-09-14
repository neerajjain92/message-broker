# MESSAGE QUEUE

We have to design a message queue supporting publisher-subscriber model, It should support following operations:

- Multiple topics where messages can be published.
- Publisher can choose a particular topic to publish messages.
- Subscriber should be able to subscribe to any topic.
- Whenever a message is published to a topic, all the subscriber who are subscribed to that topic, should receive the
  message.
- Ability to reset offset for a particular subscriber, i.e Subscriber should be able to read messages from the start.
    - Basically replaying the messages in the topic for a particular subscriber.
- Multiple subscriber can run simultaneously.

