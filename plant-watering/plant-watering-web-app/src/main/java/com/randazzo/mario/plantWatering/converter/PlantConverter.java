package com.randazzo.mario.plantWatering.converter;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.randazzo.mario.plantWatering.converter.annotation.PersonType;
import com.randazzo.mario.plantWatering.converter.annotation.PlantType;
import com.randazzo.mario.plantWatering.dto.PersonDTO;
import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.model.Plant;

/**
 * Implementation of a {@link Converter} for {@link Plant} data type.
 * 
 * @author Mario Randazzo
 *
 * @see PlantType
 *
 */
@Stateless
@PlantType
public class PlantConverter implements Converter<Plant, PlantDTO> {

	private static final long serialVersionUID = 7899174089635081025L;

	@Inject
	@PersonType
	Converter<Person, PersonDTO> personConverter;
	
	@Override
	public PlantDTO entityToDto(Plant entity) {
		if(entity == null) return null;
		
		PlantDTO dto = new PlantDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		return dto;
	}

	@Override
	public Plant dtoToEntity(PlantDTO dto) {
		if(dto == null) return null;
		
		Plant entity = new Plant();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		return entity;
	}

}
