package com.rede.stagingapi.model;


import com.rede.stagingapi.model.enums.TempBand;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    private String upc;
    private TempBand tempBand;
    private String name;
    private double weight;

    @Embedded
    private ShelfLocation location;

}
