package com.example.weatherinfo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherinfo.model.dto.WeatherInfoDTO;
import com.example.weatherinfo.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherInfoDTO> getWeather(
    	@RequestParam(name = "pincode", required = true) String pincode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) {

        WeatherInfoDTO weatherInfo = weatherService.getWeatherForPincode(pincode, forDate);
        return ResponseEntity.ok(weatherInfo);
    }
}
//@RequestParam(name = "pincode", required = true) String pincode,
//@RequestParam(name = "date", required = false) String date)
//@RequestParam String pincode,