package com.enrique.actualizacionEstados.service;


import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.repository.PackageRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PackageStatusUpdater {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PackageRepository packageRepository;

    public PackageStatusUpdater(KafkaTemplate<String, String> kafkaTemplate, PackageRepository packageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.packageRepository = packageRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void updateStatus() {
        Pack pkg = new Pack("1","primer paquete","Completado");/* for (Pack pkg : packageRepository.findAll()) {
            if ("pending".equals(pkg.getStatus())) {
                pkg.setStatus("in transit");
            } else if ("in transit".equals(pkg.getStatus())) {
                pkg.setStatus("delivered");
            }*/
            kafkaTemplate.send("package-status-update", pkg.getId(), pkg.toString());
        System.out.println(pkg.getId());
        //}
        System.out.println("Updated package statuses");
    }
}
