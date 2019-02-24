package com.randazzo.mario.plantWatering.messaging;

import java.lang.reflect.Type;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.MeasureType;
import com.randazzo.mario.plantWatering.dao.MeasureDAO;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/pl/measureTopic"), })
public class MeasureListener implements MessageListener {

	private Gson gson;

	@Inject
	private MeasureDAO measureDAO;
	
	@Inject
	@MeasureType
	private Converter<Measure, MeasureDTO> measureConverter;

	@PostConstruct
	private void init() {
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

					@Override
					public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
							throws JsonParseException {
						return json == null ? null : new Date(json.getAsLong());
					}
				})
				.create();
	}
	
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
