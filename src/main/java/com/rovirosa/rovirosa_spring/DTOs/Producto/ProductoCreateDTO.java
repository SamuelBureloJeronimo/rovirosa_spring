package com.rovirosa.rovirosa_spring.DTOs.Producto;

import java.math.BigDecimal;
import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoPostDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoCreateDTO {

    private List<CatalogoPostDTO> catalogo;

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "Debe seleccionar una marca")
    private Integer marcaId;

    @NotNull(message = "Debe indicar el precio del producto")
    @Positive(message = "El precio debe ser mayor que cero")
    private Double precio;

    @NotNull(message = "Debe indicar el peso en kilogramos")
    @Positive(message = "El peso debe ser mayor que cero")
    private Double pesoKg;

    @NotNull(message = "Debe indicar el volumen en metros cúbicos")
    @Positive(message = "El volumen debe ser mayor que cero")
    private BigDecimal volM3;

    // Imagen se sube por separado vía MultipartFile, no se valida aquí
    // Getters y Setters
    public List<CatalogoPostDTO> getCatalogo() { return catalogo; }
    public void setCatalogo(List<CatalogoPostDTO> catalogos) { this.catalogo = catalogos; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getMarcaId() { return marcaId; }
    public void setMarcaId(Integer marcaId) { this.marcaId = marcaId; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getVolM3() { return volM3; }
    public void setVolM3(BigDecimal volM3) { this.volM3 = volM3; }
}
