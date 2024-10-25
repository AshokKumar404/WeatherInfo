package com.example.weatherinfo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.weatherinfo.model.dto.LatLongDTO;

@Service
public class GeocodingService {

    private final String geocodingApiUrl = "https://api.openweathermap.org/geo/1.0/zip?zip={pincode},IN&appid={apiKey}";
    private final String apiKey = "your-openweather-api-key";  // Add your OpenWeather API Key

    public LatLongDTO getLatLongByPincode(String pincode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = geocodingApiUrl.replace("{pincode}", pincode).replace("{apiKey}", apiKey);
        return restTemplate.getForObject(url, LatLongDTO.class);  // Assuming LatLongDTO matches the response structure
    }
}
