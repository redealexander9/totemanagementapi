package com.rede.stagingapi.controller;


import com.rede.stagingapi.exception.ItemNotFoundException;
import com.rede.stagingapi.exception.ToteNotFoundException;
import com.rede.stagingapi.exception.ToteWarnings;
import com.rede.stagingapi.model.*;
import com.rede.stagingapi.repository.ItemRepository;
import com.rede.stagingapi.repository.ToteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/totes")
public class ToteController {

    private final ToteRepository toteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ToteController.class);
    private final ItemRepository itemRepository;

    public ToteController(ToteRepository toteRepository, ItemRepository itemRepository){
        this.toteRepository = toteRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public Tote createTote(@Valid @RequestBody Tote tote){
        tote.setStatus(ToteStatus.PICKING);
        return toteRepository.save(tote);
    }
    public boolean locationFull(String location, String osn){   // Checks if location has 2 or more orders that don't match tote being staged
        List<Tote> stagedTotes = toteRepository.findByStatusAndLocation(ToteStatus.STAGED, location);
        logger.info("stink");
        logger.info(location);
        List<Tote> otherOrders = stagedTotes.stream()
                .filter(t -> !t.getOsn().equals(osn))
                .toList();
        return otherOrders.size() >= 2;
    }
    @PostMapping("/{id}/edit")
    public Tote editTote(@PathVariable long id, @RequestBody Tote editedTote){
        Tote tote = toteRepository.findById(id).orElseThrow(() -> new ToteNotFoundException(id));
        int numItems = editedTote.getNumItems();

        //tote.setNumItems(numItems);
        toteRepository.save(tote);
        return tote;
    }

    @PostMapping("/{id}/addItem")
    public Tote addItemToTote(@PathVariable long id, @RequestBody long upc){
        Tote tote = toteRepository.findById(id).orElseThrow(() -> new ToteNotFoundException(id));
        ToteItem itemToAdd = itemRepository.findById(upc).orElseThrow(() -> new ItemNotFoundException(upc));
        tote.addItem(itemToAdd);
        toteRepository.save(tote);
        return tote;
    }
    @PostMapping("/{id}/stage")
    public ResponseEntity<?> stageTote(@PathVariable Long id, @RequestBody StageToteRequest request){
        Tote tote = toteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tote not found"));
        String stagingLocation = request.getLocation();

        TempBand temp = tote.getTemp();
        String osn = tote.getOsn();
        Boolean confirm = request.getConfirm();
        List<ToteWarnings> warnings = new ArrayList<>();

        if (tote.getNumItems() == 0) {
            warnings.add(ToteWarnings.EMPTY_TOTE);
        }
        if(!stagingLocation.toLowerCase().contains(temp.toString().toLowerCase())){
            warnings.add(ToteWarnings.INCORRECT_TEMP_BAND);
        }
        if(locationFull(stagingLocation, osn)){
            warnings.add(ToteWarnings.LOCATION_FULL);
        }
        //if(temp == TempBand.CHILLED && )
        if(!warnings.isEmpty() && !confirm){
            return ResponseEntity.ok(Map.of(
                    "warnings", warnings,
                    "canStage", false,
                    "requiresConfirmation", true
            ));
        }

        tote.setLocation(stagingLocation);
        tote.setStatus(ToteStatus.STAGED);
        toteRepository.save(tote);

        return ResponseEntity.ok(Map.of(
                "tote", tote,
                "warnings", warnings,
                "canStage", true
        ));

    }

    @GetMapping     // Handle GET request
    public List<Tote> getAllTotes(){
        return toteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tote getToteById(@PathVariable Long id){
        return toteRepository.findById(id).orElseThrow(() -> new ToteNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTote(@PathVariable Long id){
        if(!toteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
