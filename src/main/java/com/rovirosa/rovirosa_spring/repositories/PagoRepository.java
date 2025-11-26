package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Modifying
    @Query("UPDATE Pago p SET p.estado = :status WHERE p.id = :id")
    int updatePagoStatus(@Param("id") Integer id, @Param("status") String status);
    
}
