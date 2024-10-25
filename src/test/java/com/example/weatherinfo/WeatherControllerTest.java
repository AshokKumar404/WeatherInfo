package com.example.weatherinfo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.weatherinfo.controller.WeatherController;
import com.example.weatherinfo.model.dto.WeatherInfoDTO;
import com.example.weatherinfo.service.WeatherService;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    public void setup() {
        // Initialization code, if necessary
    }

    @Test
    public void testGetWeather() throws Exception {
        String pincode = "411014";
        WeatherInfoDTO weatherInfo = new WeatherInfoDTO();
        weatherInfo.setTemperature(25.0);
        weatherInfo.setHumidity(60.0);
        weatherInfo.setDescription("Clear");

        when(weatherService.getWeatherForPincode(pincode, LocalDate.of(2020, 10, 15))).thenReturn(weatherInfo);

        mockMvc.perform(get("/api/weather")
                .param("pincode", pincode)
                .param("forDate", "2020-10-15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(25.0))
                .andExpect(jsonPath("$.humidity").value(60.0))
                .andExpect(jsonPath("$.description").value("Clear"));
    }
}
