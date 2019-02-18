package com.randazzo.mario.plantWatering.converter;

import javax.inject.Inject;

import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

public class MeasureConverter implements Converter<Measure, MeasureDTO> {

	@Inject PlantConverter plantConverter;
	
	@Override
	public MeasureDTO entityToDto(Measure entity) {
		MeasureDTO dto = new MeasureDTO();
		dto.setExternalHumidity(entity.getExternalHumidity());
		dto.setExternalTemperature(entity.getExternalTemperature());
		dto.setInternalHumidity(entity.getInternalHumidity());
		dto.setInternalTemperature(entity.getInternalTemperature());
		dto.setPlant(plantConverter.entityToDto(entity.getPlant()));
		return dto;
	}

	@Override
	public Measure dtoToEntity(MeasureDTO dto) {
		Measure entity = new Measure();
		entity.setExternalHumidity(dto.getExternalHumidity());
		entity.setExternalTemperature(dto.getExternalTemperature());
		entity.setInternalHumidity(dto.getInternalHumidity());
		entity.setInternalTemperature(dto.getInternalTemperature());
		entity.setPlant(plantConverter.dtoToEntity(dto.getPlant()));
		return null;
	}


}
