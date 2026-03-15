package com.rede.stagingapi.model;

import com.rede.stagingapi.model.enums.OrderType;
import com.rede.stagingapi.model.enums.TempBand;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class ToteUpdateRequest {
    private TempBand temp;
    private String location;
    private OrderType type;
    private LocalDateTime firstItemPickedAt;
    private LocalDateTime pickWalkFinishedAt;
    private LocalDateTime pickWalkDueAt;
    private String batchId;
    private Boolean isFragile;
    private String sequenceNumber;
    private String orderNumber;
}
