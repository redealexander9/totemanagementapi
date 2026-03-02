package com.rede.stagingapi.model;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@JsonPropertyOrder({"id","product","tote","quantityOrdered","quantityPicked"})
public class ToteItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Tote tote;

    private Integer quantityOrdered;
    private Integer quantityPicked;


}
