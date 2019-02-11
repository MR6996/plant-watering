package com.randazzo.mario.plantWatering.converter;

import com.randazzo.mario.plantWatering.dto.security.UserDTO;
import com.randazzo.mario.plantWatering.security.model.User;

public class UserConverter implements Converter<User, UserDTO> {

	@Override
	public UserDTO entityToDto(User entity) {
		UserDTO dto = new UserDTO();
		dto.setLoginName(entity.getLoginName());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setActivationCode(entity.getActivationCode());
		return dto;
	}

}
