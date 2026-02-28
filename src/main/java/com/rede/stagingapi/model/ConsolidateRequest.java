package com.rede.stagingapi.model;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsolidateRequest {
    private Long moveItemsFromId;
    private Long moveItemsToId;
}
