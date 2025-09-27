package com.rovirosa.rovirosa_spring.clases;

import java.time.LocalDateTime;

public class CodigoVerificacion {
    private String codigo;
    private LocalDateTime expira;

    public CodigoVerificacion(String codigo, LocalDateTime expira) {
        this.codigo = codigo;
        this.expira = expira;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getExpira() {
        return expira;
    }

    public void setExpira(LocalDateTime expira) {
        this.expira = expira;
    }

    
}
