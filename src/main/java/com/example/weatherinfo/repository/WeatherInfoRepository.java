package com.example.weatherinfo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weatherinfo.model.WeatherInfoEntity;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfoEntity, Long> {
    Optional<WeatherInfoEntity> findByPincodeAndWeatherDate(String pincode, LocalDate date);
}
