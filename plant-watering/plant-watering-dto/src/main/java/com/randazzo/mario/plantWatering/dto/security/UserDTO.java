package com.randazzo.mario.plantWatering.dto.security;

public class UserDTO {

	private String loginName;

	private String activationCode;

	private String firstName;

	private String lastName;

	public UserDTO() {
		super();
	}

	public UserDTO(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public void invalidateActivationCode() {
		this.activationCode = null;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
