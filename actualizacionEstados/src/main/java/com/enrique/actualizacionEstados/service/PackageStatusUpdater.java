package com.enrique.actualizacionEstados.service;


import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.repository.PackageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageStatusUpdater {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PackageRepository packageRepository;



    public void updateStatus() {
        Pack pkg = new Pack("1","primer paquete","Completado");
        ObjectMapper objectMapper = new ObjectMapper();
        /*Message<Pack> message = MessageBuilder
                .withPayload(pkg)
                .setHeader(KafkaHeaders.TOPIC, "package-status-update")
                .build();
        /* for (Pack pkg : packageRepository.findAll()) {

            if ("pending".equals(pkg.getStatus())) {
                pkg.setStatus("in transit");
            } else if ("in transit".equals(pkg.getStatus())) {
                pkg.setStatus("delivered");
            }*/
        try {
            String packJson = objectMapper.writeValueAsString(pkg);
            kafkaTemplate.send("package-status-updatee", packJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //}
        System.out.println("Updated package statuses");
    }
}
