package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.models.DatoTransferencia;

public interface DatoTransferenciaRepository extends JpaRepository<DatoTransferencia, Integer> {

    DatoTransferencia findFirstBy();
    
}
