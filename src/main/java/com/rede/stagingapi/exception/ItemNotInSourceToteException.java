package com.rede.stagingapi.exception;

public class ItemNotInSourceToteException extends RuntimeException {
    public ItemNotInSourceToteException(String message) {
        super(message);
    }
    public ItemNotInSourceToteException(Long itemId, Long sourceToteId){
        super("Item " + itemId + " not in source tote: " + sourceToteId);
    }
}
