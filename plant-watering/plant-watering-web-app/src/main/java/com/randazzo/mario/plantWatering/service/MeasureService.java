package com.randazzo.mario.plantWatering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.picketlink.Identity;

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.MeasureType;
import com.randazzo.mario.plantWatering.dao.PlantDAO;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;
import com.randazzo.mario.plantWatering.model.Plant;
import com.randazzo.mario.plantWatering.security.model.User;

/**
 * RESTFul endpoint responsible for handle basic operation on {@link Measure} data type.
 * 
 * @author Mario Randazzo
 *
 */
@Path("/private/measure")
@Stateless
public class MeasureService {

	@Inject
	private PlantDAO plantDao;
	
	@Inject
	@MeasureType
	private Converter<Measure, MeasureDTO> measureConverter;
	
	@Inject
	private Identity identity;
	
	@GET
	@Path("/{plantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MeasureDTO> allPlantMeasures(@PathParam("plantId") Long plantId) {
		User loggedUser = (User) identity.getAccount();
		Plant plant = plantDao.findByIdPersonEmail(plantId, loggedUser.getLoginName());
		
		if(plant != null)
			return plant.getMeasures().stream()
					.map(p->{return measureConverter.entityToDto(p);})
					.collect(Collectors.toList());
		else
			return new ArrayList<MeasureDTO>();
	}

}
