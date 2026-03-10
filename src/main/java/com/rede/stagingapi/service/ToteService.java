package com.rede.stagingapi.service;

import com.rede.stagingapi.exception.ToteNotFoundException;
import com.rede.stagingapi.model.Tote;
import com.rede.stagingapi.model.enums.ToteStatus;
import com.rede.stagingapi.repository.ToteRepository;
import org.springframework.stereotype.Service;

@Service
public class ToteService {

    private final ToteRepository toteRepository;

    public ToteService(ToteRepository toteRepository) {
        this.toteRepository = toteRepository;
    }

    public Tote consolidate(Long targetId, Long sourceId){
        Tote target = toteRepository.findById(targetId).orElseThrow(() -> new ToteNotFoundException(targetId));
        Tote source = toteRepository.findById(sourceId).orElseThrow(() -> new ToteNotFoundException(sourceId));
        System.out.println(target.getStatus().toString());
        System.out.println(source.getStatus().toString());
        source.setLocation(target.getLocation());   // Set location of source tote to keep it from affecting staging stats
        target.mergeItemsFrom(source);
        source.setStatus(ToteStatus.STAGED);
        toteRepository.deleteById(sourceId);
        return target;
    }
}
