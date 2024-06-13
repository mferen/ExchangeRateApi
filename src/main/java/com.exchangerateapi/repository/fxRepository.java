package com.exchangerateapi.repository;

import com.exchangerateapi.entity.FXData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface fxRepository extends JpaRepository<FXData, Long> {

    FXData findTopByFxTypeOrderByIdDesc(String currency);
    List<FXData> findAll();



}


