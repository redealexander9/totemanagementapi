package com.rede.stagingapi.controller;

import com.rede.stagingapi.exception.CreateItemException;
import com.rede.stagingapi.exception.ItemNotFoundException;
import com.rede.stagingapi.model.ToteItem;
import com.rede.stagingapi.repository.ItemRepository;
import com.rede.stagingapi.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemController(ItemRepository itemRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ToteItem createItem(@Valid @RequestBody ToteItem item){
        String upc = item.getUpc();
        if(upc == null){
            throw new CreateItemException("UPC cannot be null");
        }
        productRepository.findById(upc).orElseThrow(() -> new ItemNotFoundException(upc));
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
