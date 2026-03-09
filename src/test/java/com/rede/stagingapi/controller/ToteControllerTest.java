package com.rede.stagingapi.controller;

import com.rede.stagingapi.exception.ToteMergeException;
import com.rede.stagingapi.model.Tote;
import com.rede.stagingapi.model.ToteItem;
import com.rede.stagingapi.model.enums.TempBand;
import com.rede.stagingapi.model.enums.ToteStatus;
import com.rede.stagingapi.repository.ToteRepository;
import com.rede.stagingapi.repository.ItemRepository;
import com.rede.stagingapi.service.ToteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ToteControllerTest {

    @Mock
    private ToteRepository toteRepository;

    @InjectMocks
    private ToteService toteService;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ToteController toteController;


    @Test
    void consolidateTotes_throwsException_whenTempBandsDiffer(){
        Tote chilledTote = new Tote(TempBand.CHILLED);
        Tote ambientTote = new Tote(TempBand.AMBIENT);
        chilledTote.setId(1L);
        ambientTote.setId(2L);
        chilledTote.setSequenceNumber("1234");
        System.out.println(chilledTote.getId());
        ToteItem chilledItem = new ToteItem("123456789123");
        ToteItem ambientItem = new ToteItem("123456789124");
        chilledTote.addItem(chilledItem);
        ambientTote.addItem(ambientItem);
        when(toteRepository.findById(1L))
                .thenReturn(Optional.of(chilledTote));
        when(toteRepository.findById(2L))
                .thenReturn(Optional.of(ambientTote));

        assertThrows(ToteMergeException.class, () -> {
           Tote t = toteService.consolidate(1L, 2L);
        });

    }


    @Test
    void createTote_createsNewToteWithDefaultProperties(){
        Tote tote = new Tote();
        assertNotNull(tote.getToteCreatedTime(), "ToteCreatedTime should not be null");
        assertNotNull(tote.getItems(), "items should not be null");
        assertEquals(ToteStatus.PICKING, tote.getStatus(), "Tote status should be PICKING after tote creation");

    }


}
