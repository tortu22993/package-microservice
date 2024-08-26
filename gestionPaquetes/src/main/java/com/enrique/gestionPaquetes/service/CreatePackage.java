package com.enrique.gestionPaquetes.service;

import com.enrique.gestionPaquetes.entity.Pack;
import com.enrique.gestionPaquetes.repository.PackageRepository;
import com.enrique.gestionPaquetes.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class CreatePackage {

    PackageRepository packageRepository;

    public Pack createPack(Pack pack) throws NoSuchAlgorithmException{

        String hash = HashUtils.calculateHash(pack);
        pack.setDataHash(hash);
        packageRepository.save(pack);
        return pack;
    }

    //hacer otros microservicios, llamar mediante feign al microservicio de actualizaciones paraa que haga un duplicado del nuevo objeto
}
