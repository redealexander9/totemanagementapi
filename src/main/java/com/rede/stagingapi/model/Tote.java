package com.rede.stagingapi.model;
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
public class Tote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Temperature band required")
    private TempBand temp; // Ambient, Chilled, Frozen, Hot, Unknown

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
    @Pattern(regexp = "^[0-9]{4}$", message = "Code must be 4 digits, each 0-9")
    private String osn;
    private String orderNumber;
    private List<String> pickerIds = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToteItem> items = new ArrayList<>();
    public int getNumItems() {
        return items.size();
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
}
