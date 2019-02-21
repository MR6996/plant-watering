package com.randazzo.mario.plantWatering.service;

import java.io.Serializable;
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

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.PlantType;
import com.randazzo.mario.plantWatering.dao.PlantDAO;
import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantWatering.model.Plant;
import com.randazzo.mario.plantWatering.utils.MessageBuilder;

@Path("/private/plant")
@Stateless
public class PlantService implements Serializable {

	private static final long serialVersionUID = -3785858585362912224L;

	@Inject
	private PlantDAO plantDAO;

	@Inject
	@PlantType
	private Converter<Plant, PlantDTO> plantConverter;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(PlantDTO request) {
		MessageBuilder message;

		try {
			plantDAO.save(plantConverter.dtoToEntity(request));
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
			plantDAO.update(plantConverter.dtoToEntity(request));
			message = MessageBuilder.ok().message("Updated successfully!");
		} catch (Exception e) {
			message = MessageBuilder.badRequest().message(e.getCause() + ": " + e.getMessage());
		}

		return message.build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlantDTO findById(@PathParam("id") Long id) {
		return plantConverter.entityToDto(plantDAO.findById(id));
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlantDTO> findAll() {
		return plantDAO.findAll().stream()
				.map(p->{return plantConverter.entityToDto(p);})
				.collect(Collectors.toList());
	}
	
}
