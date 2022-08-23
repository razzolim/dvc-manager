package com.azzolim.renan.dvc.manager.domain.repository;

import com.azzolim.renan.dvc.manager.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(nativeQuery = true, value = "SELECT SUM(sc.AMOUNT) FROM DEVICE dev " +
            "INNER JOIN DEVICE_SERVICE ds ON dev.ID = ds.DEVICE_ID " +
            "INNER JOIN SERVICE s ON s.ID = ds.SERVICE_ID " +
            "INNER JOIN SERVICE_COST sc ON sc.SERVICE_ID = s.ID AND dev.type_id = sc.Device_TYPE_ID")
    BigDecimal sumAllServices();

    @Query(nativeQuery = true, value = "SELECT SUM(sc.AMOUNT) FROM DEVICE dev " +
            "INNER JOIN DEVICE_SERVICE ds ON dev.ID = ds.DEVICE_ID " +
            "INNER JOIN SERVICE s ON s.ID = ds.SERVICE_ID " +
            "INNER JOIN SERVICE_COST sc ON sc.SERVICE_ID = s.ID AND dev.type_id = sc.Device_TYPE_ID" +
            " WHERE dev.ID = ?1")
    BigDecimal sumAllServicesByDeviceId(Long deviceId);

}
