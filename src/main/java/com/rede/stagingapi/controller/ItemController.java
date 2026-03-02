package com.rede.stagingapi.controller;

import com.rede.stagingapi.exception.ItemNotFoundException;
import com.rede.stagingapi.model.ToteItem;
import com.rede.stagingapi.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public ToteItem createItem(@Valid @RequestBody ToteItem item){
        return itemRepository.save(item);
    }

    @GetMapping
    public List<ToteItem> getAllItems(){
        return itemRepository.findAll();
    }


    @GetMapping("/{id}")
    public ToteItem getItemById(@PathVariable Long id){
       return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        if(!itemRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
