package com.enrique.actualizacionEstados.service;


import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.repository.PackageRepository;
import com.enrique.actualizacionEstados.utils.HashUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class PackageStatusUpdater {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PackageRepository packageRepository;



    public void updateStatus() {

        ObjectMapper objectMapper = new ObjectMapper();

         for (Pack pkg : packageRepository.findAll()) {
             if (isModified(pkg)) {
                 try {
                     String packJson = objectMapper.writeValueAsString(pkg);
                     kafkaTemplate.send("package-status-updatee", packJson);
                 } catch (JsonProcessingException e) {
                     e.printStackTrace();
                 }
             }
         }

        System.out.println("Updated package statuses");
    }

    private boolean isModified(Pack pack) {
        // Compara el hash almacenado con el nuevo hash calculado
        try {
            String currentHash = HashUtils.calculateHash(pack);
            return !currentHash.equals(pack.getDataHash());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

    }
