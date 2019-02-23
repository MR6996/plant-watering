package com.randazzo.mario.plantWatering.messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;
import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.MeasureType;
import com.randazzo.mario.plantWatering.dao.MeasureDAO;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/pl"), })
public class MeasureListener implements MessageListener {

	Gson gson = new Gson();

	@Inject
	MeasureDAO measureDAO;
	
	@Inject
	@MeasureType
	Converter<Measure, MeasureDTO> measureConverter;

	@Override
	public void onMessage(Message measure) {

		try {
			if (measure instanceof TextMessage) {
				TextMessage msg = (TextMessage) measure;
				System.out.println(msg.getText());

				MeasureDTO m = gson.fromJson(msg.getText(), MeasureDTO.class);
				measureDAO.save(measureConverter.dtoToEntity(m));
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
