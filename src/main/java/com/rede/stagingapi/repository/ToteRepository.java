package com.rede.stagingapi.repository;

import com.rede.stagingapi.model.Tote;
import com.rede.stagingapi.model.enums.ToteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToteRepository extends JpaRepository<Tote, Long>{
List<Tote> findByStatusAndLocation(ToteStatus status, String location);

@Query("SELECT t FROM Tote t WHERE t.batchId = :batchId AND t.location = :location")
    List<Tote> findByLocationAndTrip(@Param("tripId") String tripId, @Param("location") String location);

@Query("SELECT t FROM Tote t JOIN t.shopperIds i " + "WHERE i = :shopperId AND t.status = 'UNSTAGED'")
    List<Tote> findUnstagedByShopperId(@Param("pickerId") String pickerId);

@Query("SELECT t FROM Tote t WHERE t.status = 'UNSTAGED'")
    List<Tote> findAllUnstaged();
}
