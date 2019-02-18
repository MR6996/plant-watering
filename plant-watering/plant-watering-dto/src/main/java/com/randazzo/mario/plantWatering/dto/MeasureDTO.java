package com.randazzo.mario.plantWatering.dto;

import java.io.Serializable;

public class MeasureDTO implements Serializable {

	private static final long serialVersionUID = -2342075750185903700L;

	private PlantDTO plant;
	private float externalTemperature;
	private float externalHumidity;
	private float internalTemperature;
	private float internalHumidity;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(externalHumidity);
		result = prime * result + Float.floatToIntBits(externalTemperature);
		result = prime * result + Float.floatToIntBits(internalHumidity);
		result = prime * result + Float.floatToIntBits(internalTemperature);
		result = prime * result + ((plant == null) ? 0 : plant.hashCode());
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
		MeasureDTO other = (MeasureDTO) obj;
		if (Float.floatToIntBits(externalHumidity) != Float.floatToIntBits(other.externalHumidity))
			return false;
		if (Float.floatToIntBits(externalTemperature) != Float.floatToIntBits(other.externalTemperature))
			return false;
		if (Float.floatToIntBits(internalHumidity) != Float.floatToIntBits(other.internalHumidity))
			return false;
		if (Float.floatToIntBits(internalTemperature) != Float.floatToIntBits(other.internalTemperature))
			return false;
		if (plant == null) {
			if (other.plant != null)
				return false;
		} else if (!plant.equals(other.plant))
			return false;
		return true;
	}

	

}
