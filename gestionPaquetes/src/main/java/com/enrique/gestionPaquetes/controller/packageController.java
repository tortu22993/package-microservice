package com.enrique.gestionPaquetes.controller;

import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.service.CreatePackage;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/packages")
public class packageController {

    private static final Logger logger = LoggerFactory.getLogger(packageController.class);

    private Map<String, Pack> packages = new HashMap<>();

    @Autowired
    CreatePackage createPackage;

    @PostMapping("/new")
   // @Secured({ "ROLE_USER"})
    public ResponseEntity<String> newPackages(@RequestBody Pack pack) throws NoSuchAlgorithmException {

        createPackage.createPack(pack);

        return ResponseEntity.ok("OK");
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<String> newPackages(HttpServletRequest request) {
        String roles = (String) request.getAttribute("roles");
        String rolesHeader = request.getHeader("X-Roles");
        System.out.println("Roles recibidos en el microservicio de paquetería: " + roles);

        System.out.println("Roles del usuario: " + roles);

        return ResponseEntity.ok(packages.toString());
    }
}
