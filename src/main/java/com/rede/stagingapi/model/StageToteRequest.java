package com.rede.stagingapi.model;
import lombok.Data;

@Data
public class StageToteRequest {
    private String location;
    private Boolean confirm;

    public Boolean getConfirm(){
        return confirm != null && confirm;
    }
}
