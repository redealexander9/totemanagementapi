package com.rede.stagingapi.model;


import com.rede.stagingapi.model.enums.TempBand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    private String upc;
    private TempBand temp;
    private String name;
    private double weight;

    @ManyToOne
    private Location location;
}
