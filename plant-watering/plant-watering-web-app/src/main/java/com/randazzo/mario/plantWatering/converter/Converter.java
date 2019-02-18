package com.randazzo.mario.plantWatering.converter;

public interface Converter<E, D> {

	public D entityToDto(E entity);
	
	public E dtoToEntity(D dto);
	
}
