package com.randazzo.mario.plantWatering.converter;

import javax.inject.Inject;

import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

public class MeasureConverter implements Converter<Measure, MeasureDTO> {

	@Inject PlantConverter plantConverter;
	
	@Override
	public MeasureDTO entityToDto(Measure entity) {
		MeasureDTO dto = new MeasureDTO();
		dto.setId(entity.getId());
		dto.setExternalHumidity(entity.getExternalHumidity());
		dto.setExternalTemperature(entity.getExternalTemperature());
		dto.setInternalHumidity(entity.getInternalHumidity());
		dto.setInternalTemperature(entity.getInternalTemperature());
		dto.setPlant(plantConverter.entityToDto(entity.getPlant()));
		return dto;
	}


}
