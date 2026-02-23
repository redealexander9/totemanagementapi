package com.rede.stagingapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ToteItem {

    @Id
    @Pattern(regexp = "\\d{12}", message="UPC must be exactly 12 digits")
    private String upc;

    private BigDecimal price;
    private String category;
    private String name;
    private Integer numOnHand;
    private Integer quantityOrdered;
    private Integer quantityPicked;
    private Boolean isInTote;
    private TempBand itemTemp;
    private String aisle;
    private Integer sectionNum;
    private Integer tagNum;

    @Column(nullable = false)
    private Boolean isOversized = false;
    @Column(nullable = false)
    private Boolean isFragile = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tote_id")
    private Tote tote;

}
