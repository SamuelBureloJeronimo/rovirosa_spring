package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.AppInfoDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassDTO;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;

public interface EmpresaConfigRepository extends JpaRepository<EmpresaConfig, String> {

    AppInfoDTO findFirstByOrderByRfcAsc();

    EmpresaConfigQueryDTO findProjectedBy();

    GmailPassDTO findFirstBy();

    @Modifying
    @Query("UPDATE EmpresaConfig e SET e.emailApp = :emailApp, e.codigoApp = :codigoApp")
    int updateGmailConfig(@Param("emailApp") String emailApp, @Param("codigoApp") String codigoApp);

}
