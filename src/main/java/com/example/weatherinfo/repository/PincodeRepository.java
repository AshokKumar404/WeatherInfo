package com.example.weatherinfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weatherinfo.model.PincodeEntity;

public interface PincodeRepository extends JpaRepository<PincodeEntity, Long> {
    Optional<PincodeEntity> findByPincode(String pincode);
}
