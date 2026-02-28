package com.rede.stagingapi.exception;

public class ItemNotFoundException extends ResourceNotFoundException {
    public ItemNotFoundException(Long upc){ super("Item with UPC " + upc + " not created yet");}
    public ItemNotFoundException(String message) {
        super("UPC: " + message + " could not be found in the system");

    }

}
