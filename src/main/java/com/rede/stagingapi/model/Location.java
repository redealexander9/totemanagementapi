package com.rede.stagingapi.model;

import com.rede.stagingapi.model.enums.TempBand;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aisle;
    private int section;
    private int position;
    private TempBand temp;
}
