package com.randazzo.mario.plantWatering.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.randazzo.mario.plantWatering.security.model.User;

/**
 * Represents a user in the application. This entity is linked with 
 * {@link User} entity, for link the application data model with Identity 
 * model of Picketlink.
 * 
 * @author Mario Randazzo
 *
 */
@Entity(name = "person")
public class Person {

	@Id
	private String email;

	private String firstName;
	private String lastName;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
