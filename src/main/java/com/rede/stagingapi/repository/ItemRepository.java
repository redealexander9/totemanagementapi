package com.rede.stagingapi.repository;
import com.rede.stagingapi.model.ToteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ToteItem, String> {


}
