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
public class ProducerBean implements IProducerBean {

	@Resource(lookup = "java:/ConnectionFactory")
	private QueueConnectionFactory connectionfactory;

	@Resource(lookup = "java:/jms/queue/test")
	Queue destinazione;

	@Override
	public void send(String param) {
		
		try {
			QueueConnection connessione = connectionfactory.createQueueConnection();
			QueueSession sessione = connessione.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer produttore = sessione.createProducer(destinazione);
			Message messaggio = sessione.createTextMessage(param);

			produttore.send(messaggio);

			sessione.commit();
			sessione.close();
			connessione.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
