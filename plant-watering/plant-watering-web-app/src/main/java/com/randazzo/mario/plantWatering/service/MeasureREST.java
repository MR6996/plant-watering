package com.randazzo.mario.plantWatering.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.picketlink.authorization.annotations.LoggedIn;

import com.randazzo.mario.plantWatering.dao.MeasureDAO;
import com.randazzo.mario.plantWatering.model.Measure;

@Path("/private/measure")
@Stateless
@LoggedIn
public class MeasureREST {
	
	@Inject
	MeasureDAO measureDao;
	
	@GET
	@Path("/{plantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Measure> allPlantMeasures(@PathParam("plantId") Long plantId) {
		return measureDao.findByPlantId(plantId);
	}

}
