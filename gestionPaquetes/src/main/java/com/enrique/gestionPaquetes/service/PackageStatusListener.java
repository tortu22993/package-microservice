package com.enrique.gestionPaquetes.service;


import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.repository.PackageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PackageStatusListener {

    private final PackageRepository packageRepository;

    public PackageStatusListener(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @KafkaListener(topics = "package-status-update", groupId = "package-group")
    public void listenPackageStatusUpdates(String packageId, String updatedPackage) {
        // Actualizar el estado del paquete en la base de datos
        ObjectMapper mapper = new ObjectMapper();
        try {
            Pack pack = mapper.readValue(updatedPackage, Pack.class);
            //packageRepository.save(updatedPackage);
            System.out.println("package: " + pack.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
