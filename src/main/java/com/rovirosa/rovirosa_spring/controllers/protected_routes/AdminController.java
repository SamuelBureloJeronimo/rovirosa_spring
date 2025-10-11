package com.rovirosa.rovirosa_spring.controllers.protected_routes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.services.PuntoVentaService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private PuntoVentaService puntoService;

    @GetMapping("/get-all")
    public List<PuntoVenta> getPuntos() {
        return puntoService.getPuntos();
    }

    @PostMapping("/create")
    public PuntoVenta createPunto(@RequestBody PuntoVenta punto) {
        return puntoService.createPunto(punto);
    }

    @PutMapping("/update/{id}")
    public PuntoVenta updatePunto(@PathVariable Integer id, @RequestBody PuntoVenta punto) {
        punto.setId(id);
        return puntoService.updatePunto(punto);
    }

    @PutMapping("/update-avalible-zone")
    public ResponseEntity<HashMap<String, String>> 
    updatePunto(@RequestParam Integer id, @RequestParam String zone) 
    {
        HashMap<String, String> response = new HashMap<>();
        Boolean rowAffected = puntoService.updateZone(id, zone);

        if (!rowAffected)
            return ResponseEntity.status(404).body(response);
        
        response.put("success", "Zona actualizada exitosamente.");
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePunto(@PathVariable Integer id) {
        puntoService.deletePunto(id);
    }
    
}
