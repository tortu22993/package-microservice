package com.enrique.actualizacionEstados.controller;

import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.repository.PackageRepository;
import com.enrique.actualizacionEstados.service.PackageStatusUpdater;
import com.enrique.actualizacionEstados.service.PackageUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private PackageStatusUpdater updater;

    @Autowired
    private PackageUpdate packageUpdate;

    private PackageRepository packageRepository;

    @PostMapping("/{id}/{status}")
    public ResponseEntity<?> getStatus(@PathVariable("id") String id, @PathVariable("status") String status) throws NoSuchAlgorithmException {
        Optional<Pack> optionalPack = packageRepository.findById(id);

        if (optionalPack.isEmpty()) {
            // Si el paquete no existe, devolvemos un error 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package with id " + id + " not found");
        }

        // Obtener el paquete
        Pack pack = optionalPack.get();

        // Actualizar el estado del paquete usando el servicio adecuado
        pack.setStatus(status);

        // Guardar los cambios en la base de datos
        Pack updatedPack = packageUpdate.UpdatePack(pack);

        // Devolver la respuesta con el paquete actualizado
        return ResponseEntity.ok(updatedPack);
    }

    @Scheduled(fixedRate = 30000)
    public void updateStatus() {
        updater.updateStatus();
    }
}