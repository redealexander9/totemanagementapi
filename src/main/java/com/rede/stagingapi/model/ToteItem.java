package com.rede.stagingapi.model;


import com.rede.stagingapi.model.enums.TempBand;
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

    private Integer quantityOrdered;
    private Integer quantityPicked;
    private TempBand itemTemp;
    private String aisle;
    private Integer sectionNum;
    private Integer tagNum;

    //private ToteItem substitution;

    @Column(nullable = false)
    private Boolean isOversized = false;
    @Column(nullable = false)
    private Boolean isFragile = false;



}
