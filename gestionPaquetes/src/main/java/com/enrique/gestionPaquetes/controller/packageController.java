package com.enrique.gestionPaquetes.controller;

import com.enrique.gestionPaquetes.entity.Pack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/packages")
public class packageController {

    private Map<String, Pack> packages = new HashMap<>();

    public packageController() {
        packages.put("1", new Pack("1", "Package 1", "pending"));
        packages.put("2", new Pack("2", "Package 2", "delivered"));
    }

    @GetMapping
    public ResponseEntity<Map<String, Pack>> getPackages() {
        return ResponseEntity.ok(packages);
    }
}
