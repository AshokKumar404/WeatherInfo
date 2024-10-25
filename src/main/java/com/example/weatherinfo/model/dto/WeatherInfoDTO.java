package com.example.weatherinfo.model.dto;

public class WeatherInfoDTO {

    private Double temperature;
    private Double humidity;
    private String description;
    
    
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public WeatherInfoDTO(Double temperature, Double humidity, String description) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.description = description;
	}
	
	
	public WeatherInfoDTO() {
		super();
		
	}

}
