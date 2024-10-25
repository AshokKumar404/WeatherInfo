package com.example.weatherinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.weatherinfo.model.WeatherInfoEntity;
import com.example.weatherinfo.model.dto.LatLongDTO;
import com.example.weatherinfo.model.dto.WeatherInfoDTO;
import com.example.weatherinfo.repository.PincodeRepository;
import com.example.weatherinfo.repository.WeatherInfoRepository;
import com.example.weatherinfo.service.GeocodingService;
import com.example.weatherinfo.service.WeatherServiceImpl;

public class WeatherServiceTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private PincodeRepository pincodeRepository;

    @Mock
    private WeatherInfoRepository weatherInfoRepository;

    @Mock
    private GeocodingService geocodingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherForPincode_CachedData() {
        String pincode = "411014";
        LocalDate date = LocalDate.of(2020, 10, 15);

        WeatherInfoEntity cachedWeather = new WeatherInfoEntity();
        cachedWeather.setTemperature(25.0);
        cachedWeather.setHumidity(60.0);
        cachedWeather.setDescription("Clear");

        when(weatherInfoRepository.findByPincodeAndWeatherDate(pincode, date)).thenReturn(Optional.of(cachedWeather));

        WeatherInfoDTO result = weatherService.getWeatherForPincode(pincode, date);

        assertNotNull(result);
        assertEquals(25.0, result.getTemperature());
        assertEquals(60.0, result.getHumidity());
        assertEquals("Clear", result.getDescription());
        verify(geocodingService, never()).getLatLongByPincode(any()); 
    }

    @Test
    public void testGetWeatherForPincode_NewData() {
        String pincode = "411014";
        LocalDate date = LocalDate.of(2020, 10, 15);

        LatLongDTO latLong = new LatLongDTO(18.5245, 73.8472);
        WeatherInfoDTO newWeatherInfo = new WeatherInfoDTO();
        newWeatherInfo.setTemperature(22.0);
        newWeatherInfo.setHumidity(70.0);
        newWeatherInfo.setDescription("Rainy");

        when(weatherInfoRepository.findByPincodeAndWeatherDate(pincode, date)).thenReturn(Optional.empty());
        when(geocodingService.getLatLongByPincode(pincode)).thenReturn(latLong);
        when(weatherInfoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        WeatherInfoDTO result = weatherService.getWeatherForPincode(pincode, date);

        assertNotNull(result);
        assertEquals(22.0, result.getTemperature());
        assertEquals(70.0, result.getHumidity());
        assertEquals("Rainy", result.getDescription());

        ArgumentCaptor<WeatherInfoEntity> captor = ArgumentCaptor.forClass(WeatherInfoEntity.class);
        verify(weatherInfoRepository).save(captor.capture());
        assertEquals(pincode, captor.getValue().getPincode());
    }
}
