package com.enrique.gestionPaquetes.service;


import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.repository.PackageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PackageStatusListener {

    private final PackageRepository packageRepository;

    public PackageStatusListener(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @KafkaListener(topics = "package-status-updates", groupId = "package-group")
    public void listenPackageStatusUpdates(String packageId, Pack updatedPackage) {
        // Actualizar el estado del paquete en la base de datos
        packageRepository.save(updatedPackage);
        System.out.println("Updated package status for packageId: " + packageId);
    }
}
