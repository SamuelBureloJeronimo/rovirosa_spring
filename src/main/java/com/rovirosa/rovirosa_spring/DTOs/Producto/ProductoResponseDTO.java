package com.rovirosa.rovirosa_spring.DTOs.Producto;

public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private String imagen;
    private Double precio;
    private Double pesoKg;
    private Double volM3;

    // Info adicional anidada
    private String marcaNombre;
    private String marcaLogo;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public Double getVolM3() { return volM3; }
    public void setVolM3(Double volM3) { this.volM3 = volM3; }

    public String getMarcaNombre() { return marcaNombre; }
    public void setMarcaNombre(String marcaNombre) { this.marcaNombre = marcaNombre; }

    public String getMarcaLogo() { return marcaLogo; }
    public void setMarcaLogo(String marcaLogo) { this.marcaLogo = marcaLogo; }
}

