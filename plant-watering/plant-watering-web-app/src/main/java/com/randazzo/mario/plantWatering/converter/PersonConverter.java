package com.randazzo.mario.plantWatering.converter;

import com.randazzo.mario.plantWatering.dto.PersonDTO;
import com.randazzo.mario.plantWatering.model.Person;

public class PersonConverter implements Converter<Person, PersonDTO> {

	@Override
	public PersonDTO entityToDto(Person entity) {
		PersonDTO dto = new PersonDTO();
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		return null;
	}

	

}
