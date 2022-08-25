package com.azzolim.renan.dvc.manager.domain.repository;

import com.azzolim.renan.dvc.manager.domain.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("from Service s join fetch s.serviceType")
    List<Service> findAll();
}
