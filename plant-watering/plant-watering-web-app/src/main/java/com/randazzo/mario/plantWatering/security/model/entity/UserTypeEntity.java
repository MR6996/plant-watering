package com.randazzo.mario.plantWatering.security.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.jpa.annotations.entity.IdentityManaged;
import org.picketlink.idm.jpa.model.sample.simple.IdentityTypeEntity;

import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.security.model.User;

/**
 * This entity is responsible to map {@link User}.
 * 
 * @author Mario Randazzo
 *
 */
@Entity
@IdentityManaged(User.class)
public class UserTypeEntity extends IdentityTypeEntity {

	private static final long serialVersionUID = -2880911865425443349L;

	@AttributeValue
	private String loginName;
	
    @OneToOne (cascade = CascadeType.ALL)
	@AttributeValue
	private Person person;


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
