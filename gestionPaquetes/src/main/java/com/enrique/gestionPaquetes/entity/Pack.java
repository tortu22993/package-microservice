package com.enrique.gestionPaquetes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pack {

    private String id;
    private String name;
    private String status;
}