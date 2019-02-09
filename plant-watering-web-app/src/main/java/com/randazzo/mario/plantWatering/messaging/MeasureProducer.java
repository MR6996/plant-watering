package com.randazzo.mario.plantWatering.messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

@Stateless
public class MeasureProducer implements IMeasureProducer {

	@Resource(lookup = "java:/ConnectionFactory")
	private QueueConnectionFactory connectionfactory;

	@Resource(lookup = "java:/jms/queue/pl")
	Queue destination;

	@Override
	public void send(String measure) {
		
		try {
			QueueConnection connection = connectionfactory.createQueueConnection();
			QueueSession session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);

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
