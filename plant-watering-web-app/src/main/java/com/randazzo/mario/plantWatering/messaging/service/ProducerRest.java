package com.randazzo.mario.plantWatering.messaging.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.randazzo.mario.plantWatering.messaging.IMeasureProducer;

@Path("/prod")
@Stateless
public class ProducerRest {

	@EJB
	IMeasureProducer measureProducer;
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String save(String p) {
		
		measureProducer.send(p);
		
		return "ok";
	}
	
	
}
