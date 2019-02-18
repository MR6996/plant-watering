package com.randazzo.mario.plantWatering.converter;

import com.randazzo.mario.plantWatering.dto.security.UserRegistrationDTO;
import com.randazzo.mario.plantWatering.security.model.UserRegistration;

public class UserRegistrationConverter implements Converter<UserRegistration, UserRegistrationDTO> {

	@Override
	public UserRegistrationDTO entityToDto(UserRegistration entity) {
		UserRegistrationDTO dto = new UserRegistrationDTO();
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setPassword(entity.getPassword());
		dto.setPasswordConfirmation(entity.getPasswordConfirmation());
		return dto;
	}

}
