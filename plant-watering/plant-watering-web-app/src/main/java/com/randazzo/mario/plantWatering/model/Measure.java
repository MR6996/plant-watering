package com.randazzo.mario.plantWatering.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a sensor measurement for a given {@link Plant}.
 * 
 * @author Mario Randazzo
 *
 */
@Entity(name = "measure")
public class Measure implements Serializable {

	private static final long serialVersionUID = 7951072789957877455L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(nullable = false, precision = 2)
	private float externalTemperature;

	@Column(nullable = false, precision = 2)
	private float externalHumidity;

	@Column(nullable = false, precision = 2)
	private float internalTemperature;

	@Column(nullable = false, precision = 2)
	private float internalHumidity;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getExternalTemperature() {
		return externalTemperature;
	}

	public void setExternalTemperature(float externalTemperature) {
		this.externalTemperature = externalTemperature;
	}

	public float getExternalHumidity() {
		return externalHumidity;
	}

	public void setExternalHumidity(float externalHumidity) {
		this.externalHumidity = externalHumidity;
	}

	public float getInternalTemperature() {
		return internalTemperature;
	}

	public void setInternalTemperature(float internalTemperature) {
		this.internalTemperature = internalTemperature;
	}

	public float getInternalHumidity() {
		return internalHumidity;
	}

	public void setInternalHumidity(float internallHumidity) {
		this.internalHumidity = internallHumidity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Measure other = (Measure) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
