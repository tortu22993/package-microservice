package com.enrique.gestionPaquetes.controller;

import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.service.CreatePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/packages")
public class packageController {

    private Map<String, Pack> packages = new HashMap<>();

    @Autowired
    CreatePackage createPackage;

    @PostMapping("/new")
    public ResponseEntity<String> newPackages(@RequestBody Pack pack) throws NoSuchAlgorithmException {

        createPackage.createPack(pack);

        return ResponseEntity.ok("OK");
    }

    @GetMapping
    public ResponseEntity<Map<String, Pack>> getPackages() {
        return ResponseEntity.ok(packages);
    }
}
