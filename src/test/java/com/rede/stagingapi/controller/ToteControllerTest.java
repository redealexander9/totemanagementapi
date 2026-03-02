package com.rede.stagingapi.controller;

import com.rede.stagingapi.model.Tote;
import com.rede.stagingapi.model.ToteItem;
import com.rede.stagingapi.repository.ToteRepository;
import com.rede.stagingapi.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ToteControllerTest {

    @Mock
    private ToteRepository toteRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ToteController toteController;

    @Test
    void addItemToTote_shouldAddItem(){
        Tote tote = new Tote();
        tote.setOsn("1234");
        tote.setStatus(null);

        ToteItem item = new ToteItem();

    }
}
