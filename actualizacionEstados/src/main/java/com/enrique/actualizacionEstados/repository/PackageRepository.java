package com.enrique.actualizacionEstados.repository;

import com.enrique.actualizacionEstados.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Pack, String> {
}
