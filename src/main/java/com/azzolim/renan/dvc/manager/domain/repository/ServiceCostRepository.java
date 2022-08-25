package com.azzolim.renan.dvc.manager.domain.repository;

import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCostRepository extends JpaRepository<ServiceCost, Long> {

    @Query("from ServiceCost sc join fetch sc.deviceType join fetch sc.service")
    List<ServiceCost> findAll();
}
