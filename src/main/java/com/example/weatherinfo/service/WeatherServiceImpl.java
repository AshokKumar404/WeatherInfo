package com.example.weatherinfo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.weatherinfo.model.PincodeEntity;
import com.example.weatherinfo.model.WeatherInfoEntity;
import com.example.weatherinfo.model.dto.LatLongDTO;
import com.example.weatherinfo.model.dto.WeatherInfoDTO;
import com.example.weatherinfo.repository.PincodeRepository;
import com.example.weatherinfo.repository.WeatherInfoRepository;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";
    private final String apiKey = "de6df1d34769ad5d3fca908599a9db3e";  

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Autowired
    private GeocodingService geocodingService;

    
    
    @Override
    public WeatherInfoDTO getWeatherForPincode(String pincode, LocalDate forDate) {
        Optional<WeatherInfoEntity> cachedWeather = weatherInfoRepository.findByPincodeAndWeatherDate(pincode, forDate);
        
        if (cachedWeather.isPresent()) {
            return convertToDTO(cachedWeather.get());
        } else {
            
            LatLongDTO latLong = getLatLong(pincode);
            
            
            WeatherInfoDTO newWeather = callWeatherAPI(latLong.getLatitude(), latLong.getLongitude());

           
            WeatherInfoEntity weatherEntity = convertToEntity(newWeather, pincode, forDate);
            weatherInfoRepository.save(weatherEntity);
            
            return newWeather;
        }
    }

    private LatLongDTO getLatLong(String pincode) {
        Optional<PincodeEntity> pincodeEntity = pincodeRepository.findByPincode(pincode);
        
        if (pincodeEntity.isPresent()) {
            return new LatLongDTO(pincodeEntity.get().getLatitude(), pincodeEntity.get().getLongitude());
        } else {
            LatLongDTO latLong = geocodingService.getLatLongByPincode(pincode);
            PincodeEntity newPincode = new PincodeEntity(pincode, latLong.getLatitude(), latLong.getLongitude());
            pincodeRepository.save(newPincode);
            return latLong;
        }
    }

    private WeatherInfoDTO callWeatherAPI(Double lat, Double lon) {
        RestTemplate restTemplate = new RestTemplate();
        String url = weatherApiUrl.replace("{lat}", String.valueOf(lat))
                                  .replace("{lon}", String.valueOf(lon))
                                  .replace("{apiKey}", apiKey);
        try {
            return restTemplate.getForObject(url, WeatherInfoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data from OpenWeather API", e);
        }
    }


    
    private WeatherInfoEntity convertToEntity(WeatherInfoDTO dto, String pincode, LocalDate forDate) {
        WeatherInfoEntity entity = new WeatherInfoEntity();
        entity.setPincode(pincode);
        entity.setWeatherDate(forDate);
        entity.setTemperature(dto.getTemperature());
        entity.setHumidity(dto.getHumidity());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    private WeatherInfoDTO convertToDTO(WeatherInfoEntity entity) {
        WeatherInfoDTO dto = new WeatherInfoDTO();
        dto.setTemperature(entity.getTemperature());
        dto.setHumidity(entity.getHumidity());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
