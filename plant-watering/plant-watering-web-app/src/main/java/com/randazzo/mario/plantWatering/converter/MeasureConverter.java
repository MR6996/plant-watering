package com.randazzo.mario.plantWatering.converter;

import javax.ejb.Stateless;

import com.randazzo.mario.plantWatering.converter.annotation.MeasureType;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantWatering.model.Measure;

/**
 * Implementation of a {@link Converter} for {@link Measure} data type.
 * 
 * @author Mario Randazzo
 *
 * @see MeasureType 
 *
 */
@Stateless
@MeasureType
public class MeasureConverter implements Converter<Measure, MeasureDTO> {

	private static final long serialVersionUID = -8481398592276119664L;

	@Override
	public MeasureDTO entityToDto(Measure entity) {
		if (entity == null)
			return null;

		MeasureDTO dto = new MeasureDTO();
		dto.setExternalHumidity(entity.getExternalHumidity());
		dto.setExternalTemperature(entity.getExternalTemperature());
		dto.setInternalHumidity(entity.getInternalHumidity());
		dto.setInternalTemperature(entity.getInternalTemperature());
		dto.setDate(entity.getDate());
		return dto;
	}

	@Override
	public Measure dtoToEntity(MeasureDTO dto) {
		if (dto == null)
			return null;

		Measure entity = new Measure();
		entity.setExternalHumidity(dto.getExternalHumidity());
		entity.setExternalTemperature(dto.getExternalTemperature());
		entity.setInternalHumidity(dto.getInternalHumidity());
		entity.setInternalTemperature(dto.getInternalTemperature());
		entity.setDate(dto.getDate());
		return entity;
	}

}
