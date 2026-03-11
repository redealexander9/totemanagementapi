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
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tote_id")
    private Tote tote;

    private Integer quantityOrdered = 0;
    private Integer quantityPicked = 0;

    public ToteItem() {

    }



    public String getUpc(){
        if(this.product != null){

        return product.getUpc();
        }
        return "";
    }


    public ToteItem(String upc){
        if(product == null){
            product = new Product();
        }
        product.setUpc(upc);
    }

}
