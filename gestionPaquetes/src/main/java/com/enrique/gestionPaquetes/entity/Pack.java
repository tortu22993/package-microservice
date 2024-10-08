package com.enrique.gestionPaquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Package")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pack {

    @Id
    @Column(name="id", updatable = false)
    private String id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "dataHash", nullable = true)
    private String dataHash;
}