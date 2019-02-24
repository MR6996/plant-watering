package com.randazzo.mario.plantWatering.converter;

import java.io.Serializable;

/**
 * Define a Converter between an Entity {@code E} and i relative DTO {@code D}.
 * 
 * For resolve ambiguity, the implementation is qualified with relative qualifiers.
 * 
 * @author Mario Randazzo
 *
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface Converter<E, D> extends Serializable {

	/**
	 * Convert the entity in relative DTO.
	 * 
	 * @param entity an entity of type {@code T}
	 * @return relative DTO
	 */
	public D entityToDto(E entity);
	
	/**
	 * Convert the DTO in relative Entity.
	 * 
	 * @param dto a DTO of Type {@code D}
	 * @return relative entity
	 */
	public E dtoToEntity(D dto);
	
}
