package com.example.weatherinfo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private LocalDate weatherDate;
    private Double temperature;
    private Double humidity;
    private String description;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public LocalDate getWeatherDate() {
		return weatherDate;
	}
	public void setWeatherDate(LocalDate weatherDate) {
		this.weatherDate = weatherDate;
	}
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
	public WeatherInfoEntity(Long id, String pincode, LocalDate weatherDate, Double temperature, Double humidity,
			String description) {
		super();
		this.id = id;
		this.pincode = pincode;
		this.weatherDate = weatherDate;
		this.temperature = temperature;
		this.humidity = humidity;
		this.description = description;
	}
	
	
	public WeatherInfoEntity() {
		super();
		
	}

    
    
}