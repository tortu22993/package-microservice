package com.enrique.gestionPaquetes.service;


import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.repository.PackageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.DataInput;

@Service
public class PackageStatusListener {

    private final PackageRepository packageRepository;

    public PackageStatusListener(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @KafkaListener(topics = "package-status-updatee", groupId = "package-group")
    public void listenPackageStatusUpdates(String message) throws JsonProcessingException {
        // Actualizar el estado del paquete en la base de datos
        ObjectMapper mapper = new ObjectMapper();
        Pack pack = mapper.readValue(message, Pack.class);
        //packageRepository.save(updatedPackage);
        System.out.println("package: " + pack.toString());

    }
}
