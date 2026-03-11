package com.rede.stagingapi.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rede.stagingapi.exception.ToteMergeException;
import com.rede.stagingapi.model.enums.OrderType;
import com.rede.stagingapi.model.enums.TempBand;
import com.rede.stagingapi.model.enums.ToteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
@Data
@Entity
@JsonPropertyOrder({"id","osn","orderNumber","shopperIds", "temp", "status", "location", "type", "toteCreatedTime"})
public class Tote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TempBand temp = TempBand.UNKNOWN; // Ambient, Chilled, Frozen, Hot, Unknown

    @Enumerated(EnumType.STRING)
    private ToteStatus status = ToteStatus.PICKING; // Picking, Unstaged, Staged, Returned
    private String location; // Ambient 1, Chilled 2, Frozen 3...
    private OrderType type; // Pickup, Delivery
    private LocalDateTime toteCreatedTime;
    private LocalDateTime firstItemPickedAt = null; // For determining cold chain compliance
    private LocalDateTime pickWalkFinishedAt;
    private LocalDateTime pickWalkDueAt;
    private String batchId; // If delivery orders are batched
    private boolean fragile;
    @Pattern(regexp = "^[0-9]{4}$", message = "OSN must be 4 digits, each 0-9")
    private String sequenceNumber;
    private String orderNumber;
    @ElementCollection
    private List<String> shopperIds; // Uses a list so totes can be combined without losing data

    @OneToMany(mappedBy = "tote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToteItem> items = new ArrayList<>();
    public int getNumItems() {
        if(items == null){
            items = new ArrayList<>();
        }
        return items.size();

    }


    public Tote(){
        this.shopperIds = new ArrayList<>();
        toteCreatedTime = LocalDateTime.now();
    }
    public Tote(TempBand temp){
        this.shopperIds = new ArrayList<>();
        toteCreatedTime = LocalDateTime.now();
        this.temp = temp;
    }
    @PrePersist
    protected void onCreate(){
        this.toteCreatedTime = LocalDateTime.now();
    }


    public void addItem(ToteItem item){
        if(getNumItems() == 0){
            firstItemPickedAt = LocalDateTime.now();
        }
        items.add(item);

    }

    public void addShopperId(String shopperId){
        if(this.shopperIds == null){
            shopperIds = new ArrayList<>();
        }
        shopperIds.add(shopperId);

    }

    public void mergeItemsFrom(Tote source){
        if(this.sequenceNumber != null && source.sequenceNumber != null && !this.sequenceNumber.equals(source.sequenceNumber)){
            throw new ToteMergeException("Totes need to belong to the same order");
        }
        if(this.temp != source.getTemp() && source.getTemp() != TempBand.UNKNOWN){   // Temp bands are different and source temp band is known
            throw new ToteMergeException("Totes need to be from the same temperature band");
        }
        if(this.status.equals(ToteStatus.PICKING) || source.status.equals(ToteStatus.PICKING)){
            throw new ToteMergeException("Totes cannot have status of: PICKING");
        }
        source.location = "Ambient 1";  // Stage tote to keep it from affecting tote staging stats
        source.status = ToteStatus.STAGED;
        for(ToteItem sourceItem : source.getItems()){
            boolean found = false;
            for(ToteItem targetItem : this.getItems()){
                if(targetItem.getUpc().equals(sourceItem.getUpc())){
                    targetItem.setQuantityOrdered(targetItem.getQuantityOrdered() + sourceItem.getQuantityOrdered());
                    targetItem.setQuantityPicked(targetItem.getQuantityPicked() + sourceItem.getQuantityPicked());
                    found = true;
                    break;
                }
            }
            if(!found){
                this.addItem(sourceItem);
            }
        }
        //this.items.addAll(source.getItems());
        this.shopperIds.addAll(source.getShopperIds());
        source.items.clear();
    }
}
