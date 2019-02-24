package com.randazzo.mario.plantWatering.messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

@Stateless
public class MeasureProducer implements IMeasureProducer {

	@Resource(lookup = "java:/ConnectionFactory")
	private TopicConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/measureTopic")
	Topic destination;

	@Override
	public void send(String measure) {
		
		try {
			TopicConnection connection = connectionFactory.createTopicConnection();
			TopicSession session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer producer = session.createProducer(destination);
			Message measureMessage = session.createTextMessage(measure);

			producer.send(measureMessage);

			session.commit();
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
