package com.azzolim.renan.dvc.manager.domain.repository;

import com.azzolim.renan.dvc.manager.domain.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
