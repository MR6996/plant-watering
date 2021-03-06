package com.randazzo.mario.plantWatering.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.picketlink.Identity;

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.PlantType;
import com.randazzo.mario.plantWatering.dao.PlantDAO;
import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantWatering.model.Plant;
import com.randazzo.mario.plantWatering.security.model.User;
import com.randazzo.mario.plantWatering.utils.MessageBuilder;

/**
 * RESTFul endpoint responsible for handle basic operation on {@link Plant} data type.
 * 
 * @author Mario Randazzo
 *
 */
@Path("/private/plant")
@Stateless
public class PlantService {

	@Inject
	private PlantDAO plantDAO;

	@Inject
	@PlantType
	private Converter<Plant, PlantDTO> plantConverter;

	@Inject
	private Identity identity;
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(PlantDTO request) {
		MessageBuilder message;

		try {
			User loggedUser = (User) identity.getAccount();
			Plant newPlant = plantConverter.dtoToEntity(request);
			newPlant.setPerson(loggedUser.getPerson());
			
			plantDAO.save(newPlant);
			message = MessageBuilder.ok().message("Saved successfully!");
		} catch (Exception e) {
			message = MessageBuilder.badRequest().message(e.getCause() + ": " + e.getMessage());
		}

		return message.build();
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(PlantDTO request) {
		MessageBuilder message;
		
		try {
			User loggedUser = (User) identity.getAccount();
			Plant plant = plantConverter.dtoToEntity(request);
			
			if(plantDAO.findByIdPersonEmail(plant.getId(), loggedUser.getLoginName()) != null) {
				plant.setPerson(loggedUser.getPerson());
				plantDAO.update(plant);
				message = MessageBuilder.ok().message("Updated successfully!");
			}
			else 
				message = MessageBuilder.badRequest().message("You are not the owner!");
		} catch (Exception e) {
			message = MessageBuilder.badRequest().message(e.getCause() + ": " + e.getMessage());
		}

		return message.build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlantDTO findById(@PathParam("id") Long id) {
		User loggedUser = (User) identity.getAccount();
		
		return plantConverter.entityToDto(plantDAO.findByIdPersonEmail(id, loggedUser.getLoginName()));
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlantDTO> findAll() {
		User loggedUser = (User) identity.getAccount();
		
		return plantDAO.findByPersonEmail(loggedUser.getLoginName()).stream()
				.map(p->{return plantConverter.entityToDto(p);})
				.collect(Collectors.toList());
	}
	

	
}
