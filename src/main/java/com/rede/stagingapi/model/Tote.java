package com.rede.stagingapi.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rede.stagingapi.exception.ToteNotFoundException;
import com.rede.stagingapi.model.enums.OrderType;
import com.rede.stagingapi.model.enums.TempBand;
import com.rede.stagingapi.model.enums.ToteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
@Data
@Entity
@JsonPropertyOrder({"id","osn","orderNumber","pickerIds", "temp", "status", "location", "type", "toteCreatedTime"})
public class Tote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TempBand temp = TempBand.UNKNOWN; // Ambient, Chilled, Frozen, Hot, Unknown

    @Enumerated(EnumType.STRING)
    private ToteStatus status; // Picking, Unstaged, Staged, Returned
    private String location; // Ambient 1, Chilled 2, Frozen 3, Hot Case...
    private OrderType type; // Pickup, Delivery, GMD
    private LocalDateTime toteCreatedTime;
    private LocalDateTime firstItemPickedAt = null; // For determining cold chain compliance
    private LocalDateTime pickWalkFinishedAt;
    private LocalDateTime pickWalkDueAt;
    private String tripId;
    private boolean fragile;
    @Pattern(regexp = "^[0-9]{4}$", message = "OSN must be 4 digits, each 0-9")
    private String osn;
    private String orderNumber;
    private List<String> pickerIds = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToteItem> items = new ArrayList<>();
    public int getNumItems() {
        return items.size();
    }


    public Tote(){
        this.pickerIds = new ArrayList<>();
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

    public void addPickerId(String pickerId){
        if(this.pickerIds == null){
            pickerIds = new ArrayList<>();
        }
        pickerIds.add(pickerId);

    }

    public void mergeItemsFrom(Tote source){
        if(!this.osn.equals(source.osn)){
            throw new ToteNotFoundException("Totes need to belong to the same order");
        }
        if(!this.temp.equals(source.getTemp()) && !source.temp.equals(TempBand.UNKNOWN)){
            throw new ToteNotFoundException("Totes need to be from the same temperature band");
        }
        if(this.status.equals(ToteStatus.PICKING) || source.status.equals(ToteStatus.PICKING)){
            throw new ToteNotFoundException("Totes cannot have status of: PICKING");
        }
        source.location = "Ambient 1";  // Stage tote to keep it from affecting tote staging stats
        source.status = ToteStatus.STAGED;
        this.items.addAll(source.getItems());
        this.pickerIds.addAll(source.getPickerIds());
        source.items.clear();
    }
}
