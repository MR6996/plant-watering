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

import com.randazzo.mario.plantWatering.model.Person;

/**
 * Custom {@link org.picketlink.idm.model.Account} type to represent the application users.
 * The field <code>person</code> provides a link between application's data model and
 *  the identity model provided by PicketLink.
 * 
 * @author Mario Randazzo
 *
 */
@IdentityStereotype(USER)
public class User extends AbstractIdentityType implements Account {

	private static final long serialVersionUID = 128682778283610669L;

	public static final AttributeParameter ACTIVATION_CODE = QUERY_ATTRIBUTE.byName("activationCode");

	public static final QueryParameter USER_NAME = QUERY_ATTRIBUTE.byName("loginName");

	@StereotypeProperty(IDENTITY_USER_NAME)
	@AttributeProperty
	@Unique
	private String loginName;

	private Person person;

	public User() {
		super();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
