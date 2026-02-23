package com.rede.stagingapi.controller;

import com.rede.stagingapi.exception.ItemNotFoundException;
import com.rede.stagingapi.model.ToteItem;
import com.rede.stagingapi.repository.ItemRepository;
import jakarta.validation.Valid;
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
    @GetMapping("/{upc}")
    public ToteItem getItemById(@PathVariable Long upc){
       return itemRepository.findById(upc).orElseThrow(() -> new ItemNotFoundException(upc));
    }
}
