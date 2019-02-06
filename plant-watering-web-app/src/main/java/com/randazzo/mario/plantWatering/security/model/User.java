package com.randazzo.mario.plantWatering.security.model;

import static org.picketlink.idm.model.annotation.IdentityStereotype.Stereotype.USER;
import static org.picketlink.idm.model.annotation.StereotypeProperty.Property.IDENTITY_USER_NAME;

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

	/**
     * <p>Can be used to query users by their activation code.</p>
     */
    public static final AttributeParameter ACTIVATION_CODE = QUERY_ATTRIBUTE.byName("activationCode");

    /**
     * <p>Can be used to query users by their login name.</p>
     */
	public static final QueryParameter USER_NAME = QUERY_ATTRIBUTE.byName("loginName");
	
	@StereotypeProperty(IDENTITY_USER_NAME)
	@AttributeProperty
	@Unique
	private String loginName;

	@AttributeProperty
	private String activationCode;

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

}
