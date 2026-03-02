package com.rede.stagingapi.model;

import com.rede.stagingapi.model.enums.TempBand;
import jakarta.persistence.Embeddable;

@Embeddable
public class ShelfLocation {
    private String aisle;
    private int section;
    private int position;
    private int numFacings;
    private int shelfCapacity;
    private TempBand temp;

}
