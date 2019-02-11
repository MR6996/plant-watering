package com.randazzo.mario.plantWatering.security.model;

import static org.picketlink.idm.model.annotation.IdentityStereotype.Stereotype.USER;
import static org.picketlink.idm.model.annotation.StereotypeProperty.Property.IDENTITY_USER_NAME;

import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.model.AbstractIdentityType;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.annotation.AttributeProperty;
import org.picketlink.idm.model.annotation.IdentityStereotype;
import org.picketlink.idm.model.annotation.StereotypeProperty;
import org.picketlink.idm.model.annotation.Unique;
import org.picketlink.idm.query.AttributeParameter;
import org.picketlink.idm.query.QueryParameter;

@IdentityStereotype(USER)
public class User extends AbstractIdentityType implements Account {

	public static final AttributeParameter ACTIVATION_CODE = QUERY_ATTRIBUTE.byName("activationCode");

	public static final QueryParameter USER_NAME = QUERY_ATTRIBUTE.byName("loginName");

	@StereotypeProperty(IDENTITY_USER_NAME)
	@AttributeProperty
	@Unique
	private String loginName;

	@AttributeProperty
	private String activationCode;

	@AttributeValue
	private String firstName;

	@AttributeValue
	private String lastName;

	public User() {
		super();
	}

	public User(String loginName) {
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
