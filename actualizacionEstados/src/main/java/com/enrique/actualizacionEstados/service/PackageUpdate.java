package com.enrique.actualizacionEstados.service;


import com.enrique.actualizacionEstados.entity.Pack;
import com.enrique.actualizacionEstados.repository.PackageRepository;
import com.enrique.actualizacionEstados.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class PackageUpdate {

    private final PackageRepository packageRepository;

    public Pack UpdatePack(Pack pack) throws NoSuchAlgorithmException {
        String newHash = HashUtils.calculateHash(pack);

        // Si el hash ha cambiado, actualiza y guarda
        if (!newHash.equals(pack.getDataHash())) {
            pack.setDataHash(newHash);
            return packageRepository.save(pack);
        }
        return pack;
    }
}
