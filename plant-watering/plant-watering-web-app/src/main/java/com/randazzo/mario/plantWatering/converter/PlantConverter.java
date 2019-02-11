package com.randazzo.mario.plantWatering.converter;

import javax.inject.Inject;

import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantWatering.model.Plant;

public class PlantConverter implements Converter<Plant, PlantDTO> {

	@Inject
	UserConverter userConverter;
	
	@Override
	public PlantDTO entityToDto(Plant entity) {
		PlantDTO dto = new PlantDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setUser(userConverter.entityToDto(entity.getUser()));
		return dto;
	}

}
