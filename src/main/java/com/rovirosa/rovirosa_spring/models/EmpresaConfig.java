package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empresa_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmpresaConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rfc", length = 20, nullable = false)
    private String rfc;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "logo", length = 255, nullable = false)
    private String logo;

    @Column(name = "descrip", length = 255, nullable = false)
    private String descrip;

    @Column(name = "monto_min", nullable = false)
    private Integer montoMin;

    @Column(name = "comision", nullable = false)
    private Integer comision;

    @Column(name = "email_app", length = 150)
    private String emailApp;

    @Column(name = "codigo_app", length = 50)
    private String codigoApp;

   
}
