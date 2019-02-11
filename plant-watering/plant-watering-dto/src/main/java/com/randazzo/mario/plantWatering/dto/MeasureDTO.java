package com.randazzo.mario.plantWatering.dto;

public class MeasureDTO {

	private long id;
	private PlantDTO plant;
	private float externalTemperature;
	private float externalHumidity;
	private float internalTemperature;
	private float internalHumidity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
		MeasureDTO other = (MeasureDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
