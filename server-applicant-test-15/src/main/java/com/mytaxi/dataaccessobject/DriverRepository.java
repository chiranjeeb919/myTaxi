package com.mytaxi.dataaccessobject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);


    Optional<DriverDO> findByCarDO(CarDO carDO);


    Optional<DriverDO> findByIdAndCarDO(Long driverId, CarDO carDO);


    @Query("SELECT driverDO from DriverDO driverDO left outer join  driverDO.carDO as carDO where "
        + " (:userName is null or driverDO.username = :userName)"
        + " And (:onlineStatus is null or driverDO.onlineStatus = :onlineStatus)"
        + " And (:licensePlate is null or carDO.licensePlate = :licensePlate)"
        + " And (:rating is null or carDO.rating = :rating)")
    List<DriverDO> find(
        @Param("userName") String userName, @Param("onlineStatus") OnlineStatus onlineStatus,
        @Param("licensePlate") String licensePlate, @Param("rating") Float rating);

}
