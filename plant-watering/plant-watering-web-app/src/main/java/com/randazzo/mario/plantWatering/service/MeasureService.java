package com.randazzo.mario.plantWatering.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.MeasureType;
import com.randazzo.mario.plantWatering.dao.MeasureDAO;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

@Path("/private/measure")
@Stateless
public class MeasureService implements Serializable {

	private static final long serialVersionUID = 4533445215440237939L;

	@Inject
	private MeasureDAO measureDao;
	
	@Inject
	@MeasureType
	private Converter<Measure, MeasureDTO> measureConverter;
	
	@GET
	@Path("/{plantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MeasureDTO> allPlantMeasures(@PathParam("plantId") Long plantId) {
		return measureDao.findByPlantId(plantId).stream()
				.map(p->{return measureConverter.entityToDto(p);})
				.collect(Collectors.toList());
	}

}
