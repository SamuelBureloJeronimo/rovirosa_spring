package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    // Relaciones con Categoria y Marca
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @Column(name = "imagen", nullable = false, length = 100)
    private String imagen;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @Column(name = "vol_m3", nullable = false)
    private Double volM3;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros
    public Producto(Integer id, Marca marca, String imagen, String nombre, Double precio, Double pesoKg, Double volM3) {
        this.id = id;
        this.marca = marca;
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.pesoKg = pesoKg;
        this.volM3 = volM3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Double getVolM3() {
        return volM3;
    }

    public void setVolM3(Double volM3) {
        this.volM3 = volM3;
    }

}
