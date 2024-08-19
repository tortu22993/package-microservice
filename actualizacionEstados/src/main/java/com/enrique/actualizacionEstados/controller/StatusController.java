package com.enrique.actualizacionEstados.controller;

import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.service.PackageStatusUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private PackageStatusUpdater updater;



   /* @GetMapping
    public ResponseEntity<Map<String, Pack>> getStatus() {
        return ResponseEntity.ok(packages);
    }*/

    @Scheduled(fixedRate = 30000)
    public void updateStatus() {
        updater.updateStatus();
    }
}