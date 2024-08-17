package com.enrique.gestionPaquetes.repository;

import com.enrique.gestionPaquetes.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PackageRepository extends JpaRepository<Pack, String> {
}
