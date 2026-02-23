package com.rede.stagingapi.repository;

import com.rede.stagingapi.model.Tote;
import com.rede.stagingapi.model.ToteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToteRepository extends JpaRepository<Tote, Long>{
List<Tote> findByStatusAndLocation(ToteStatus status, String location);
}
