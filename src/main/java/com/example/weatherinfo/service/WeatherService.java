package com.example.weatherinfo.service;

import java.time.LocalDate;

import com.example.weatherinfo.model.dto.WeatherInfoDTO;

public interface WeatherService {
    WeatherInfoDTO getWeatherForPincode(String pincode, LocalDate forDate);
}
