package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rovirosa.rovirosa_spring.DTOs.Descuento.BannerQueryDTO;
import com.rovirosa.rovirosa_spring.models.DescuentoConfig;

public interface DescuentoConfigRepository extends JpaRepository<DescuentoConfig, Integer> {

    @Query("SELECT d.banner AS banner FROM DescuentoConfig d WHERE d.banner IS NOT NULL")
    List<BannerQueryDTO> findAllBanners();

}
