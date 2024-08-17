package com.enrique.actualizacionEstados.controller;

import com.enrique.actualizacionEstados.entity.Pack;
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
    private Map<String, Pack> packages = new HashMap<>();

    public StatusController() {
        packages.put("1", new Pack("1", "Package 1", "pending"));
        packages.put("2", new Pack("2", "Package 2", "delivered"));
    }

    @GetMapping
    public ResponseEntity<Map<String, Pack>> getStatus() {
        return ResponseEntity.ok(packages);
    }

    @Scheduled(fixedRate = 30000)
    public void updateStatus() {
        for (Pack pkg : packages.values()) {
            if ("pending".equals(pkg.getStatus())) {
                pkg.setStatus("in transit");
            } else if ("in transit".equals(pkg.getStatus())) {
                pkg.setStatus("delivered");
            }
        }
        System.out.println("Updated package statuses");
    }
}