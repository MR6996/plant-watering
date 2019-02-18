package com.randazzo.mario.plantWatering.converter;

import java.io.Serializable;

public interface Converter<E, D> extends Serializable {

	public D entityToDto(E entity);
	
	public E dtoToEntity(D dto);
	
}
