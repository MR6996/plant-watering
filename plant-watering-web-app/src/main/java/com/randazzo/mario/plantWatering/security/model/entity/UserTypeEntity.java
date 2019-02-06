package com.randazzo.mario.plantWatering.security.model.entity;


import javax.persistence.Entity;

import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.jpa.annotations.entity.IdentityManaged;
import org.picketlink.idm.jpa.model.sample.simple.IdentityTypeEntity;

import com.randazzo.mario.plantWatering.security.model.User;

@Entity
@IdentityManaged(User.class)
public class UserTypeEntity extends IdentityTypeEntity {
	
	@AttributeValue
	private String loginName;

	@AttributeValue
	private String activationCode;

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
	
	
	
}
