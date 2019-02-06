package com.randazzo.mario.plantWatering.messaging.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.randazzo.mario.plantWatering.messaging.IProducerBean;

@Path("/prod")
@Stateless
public class ProducerRest {

	@EJB
	IProducerBean producer;
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String save(String p) {
		
		producer.send(p);
		
		return "ok";
	}
	
	
}
