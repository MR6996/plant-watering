package com.randazzo.mario.plantWatering.dto;

import java.io.Serializable;
import java.sql.Date;

public class MeasureDTO implements Serializable {

	private static final long serialVersionUID = -2342075750185903700L;

	private PlantDTO plant;
	private float externalTemperature;
	private float externalHumidity;
	private float internalTemperature;
	private float internalHumidity;
	private Date date;

	public PlantDTO getPlant() {
		return plant;
	}

	public void setPlant(PlantDTO plant) {
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

}
