package com.mytaxi.service.driver;

import com.mytaxi.datatransferobject.DriverSearchDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);
    
    List<DriverDO> find(DriverSearchDTO driverSearchDTO);
    
    void selectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;
    
    void deSelectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;

}
