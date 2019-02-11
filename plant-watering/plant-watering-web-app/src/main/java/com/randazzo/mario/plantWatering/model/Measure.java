package com.randazzo.mario.plantWatering.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.randazzo.mario.plantWatering.model.Plant;

@Entity(name = "measure")
public class Measure {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	@JoinColumn
	private Plant plant;

	@Column(nullable = false, precision = 2)
	private float externalTemperature;

	@Column(nullable = false, precision = 2)
	private float externalHumidity;

	@Column(nullable = false, precision = 2)
	private float internalTemperature;

	@Column(nullable = false, precision = 2)
	private float internallHumidity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
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

	public float getInternallHumidity() {
		return internallHumidity;
	}

	public void setInternallHumidity(float internallHumidity) {
		this.internallHumidity = internallHumidity;
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
