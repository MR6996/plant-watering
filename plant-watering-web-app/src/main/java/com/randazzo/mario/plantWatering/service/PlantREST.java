package com.randazzo.mario.plantWatering.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.picketlink.authorization.annotations.LoggedIn;

import com.randazzo.mario.plantWatering.dao.PlantDAO;
import com.randazzo.mario.plantWatering.model.Plant;

@Path("/private/plant")
@Stateless
@LoggedIn
public class PlantREST implements Serializable {
	
	@Inject 
	private PlantDAO plantDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String save(Plant p) {
		Plant pCopy = new Plant();
		pCopy.setName(p.getName());
		pCopy.setType(p.getType());
		plantDAO.save(pCopy);
		
		return "ok";
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Plant findById(@PathParam("id") Long id) {
		return plantDAO.findById(id);
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Plant> findAll() {
		return plantDAO.findAll();
	}
}
